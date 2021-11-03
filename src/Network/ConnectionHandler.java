package Network;


import Logic.Waitress;
import Network.NetworkMessages.In.IncomingNetworkMessage;
import Network.NetworkMessages.NetworkMessageDecoder;
import Network.NetworkMessages.Out.OutGoingNetworkMessage;
import Utils.RestaurantsManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionHandler extends Thread {

    private Waitress waitress;
    protected Socket socket;
    private boolean running;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
        this.running = true;
    }


    public void setWaitress(Waitress waitress) {
        this.waitress = waitress;
    }

    public boolean isReachable() {
        try {
            return this.running && socket.getInetAddress().isReachable(5);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean send(OutGoingNetworkMessage networkMessage) {
        try {
            String msg = networkMessage.encode().toString();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void onMessage(IncomingNetworkMessage message) {
        if (this.waitress == null) {
            this.waitress = RestaurantsManager.getInstance().getWaitress(message.getSerialNumber());
        }
        message.visit(this.waitress);
    }

    @Override
    public void run() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            while (this.running) {
                String message = dataInputStream.readUTF();
                JSONParser p = new JSONParser();
                JSONObject JSONMessage = (JSONObject) p.parse(message);
                IncomingNetworkMessage networkMessage = (IncomingNetworkMessage) NetworkMessageDecoder.decode(this, JSONMessage);
                this.onMessage(networkMessage);
            }
        } catch (EOFException eofException) {
            System.out.println((waitress == null ? "Tablet" : waitress.getName()) + " Closed the connection");
            this.running = false;
        } catch (SocketException socketException) {
            socketException.printStackTrace();
            this.running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
