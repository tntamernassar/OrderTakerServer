package Network;


import Logic.Waitress;
import Network.NetworkMessages.IncomingNetworkMessage;
import Network.NetworkMessages.NetworkMessage;
import Network.NetworkMessages.NetworkMessageDecoder;
import Network.NetworkMessages.OutGoingNetworkMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ConnectionHandler extends Thread{

    private Waitress waitress;
    protected Socket socket;
    private boolean running;

    public ConnectionHandler(Socket socket){
        this.socket = socket;
        this.running = true;
    }


    public void setWaitress(Waitress waitress) {
        this.waitress = waitress;
    }

    public boolean isReachable(){
        try{
            return this.running && socket.getInetAddress().isReachable(5);
        }catch (Exception e){
            return false;
        }
    }

    public boolean send(OutGoingNetworkMessage networkMessage){
        try {
            String msg = networkMessage.encode().toString();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(msg);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void onMessage(IncomingNetworkMessage message){
        if (this.waitress == null) {
            this.waitress = RestaurantsManager.getInstance().getWaitress(message.getSerialNumber());
        }
        message.visit(this.waitress);
    }

    @Override
    public void run() {
        while (this.running){
            try{
                byte[] b = socket.getInputStream().readNBytes(1);
                int length = b[0];
                byte[] bytes = new byte[length];
                socket.getInputStream().read(bytes);
                JSONParser p = new JSONParser();
                JSONObject JSONMessage = (JSONObject) p.parse(new String(bytes, StandardCharsets.UTF_8));
                IncomingNetworkMessage networkMessage = (IncomingNetworkMessage)NetworkMessageDecoder.decode(this, JSONMessage);
                this.onMessage(networkMessage);
            }catch (EOFException eofException){
                eofException.printStackTrace();
                this.running = false;
            }catch (SocketException socketException){
                socketException.printStackTrace();
                this.running = false;
            }catch (Exception e){
                try{
                    this.running = false;
                }catch (Exception ex){
                    this.running = false;
                    continue;
                }
            }

        }
    }

}
