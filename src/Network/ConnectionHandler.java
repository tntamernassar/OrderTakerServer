package Network;


import Logic.Restaurant;
import Logic.Waitress;
import Network.NetworkMessages.NetworkMessage;
import Network.NetworkMessages.NetworkMessageDecoder;
import Utils.Constants;
import org.json.simple.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler extends Thread{

    protected Socket socket;
    private Waitress waitress;
    private boolean running;

    public ConnectionHandler(Socket socket, Waitress waitress){
        this.socket = socket;
        this.waitress = waitress;
        this.running = true;
    }


    public boolean isReachable(){
        try{
            return this.running && socket.getInetAddress().isReachable(5);
        }catch (Exception e){
            return false;
        }
    }

    public boolean send(NetworkMessage networkMessage){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(Constants.TCP_BUFFER_SIZE);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(networkMessage.encode());
            byte[] data = baos.toByteArray();
            socket.getOutputStream().write(data);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void run() {
        while (this.running){
            try{
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                JSONObject JSONMessage =  (JSONObject)ois.readObject();
                NetworkMessage networkMessage = NetworkMessageDecoder.decode(JSONMessage);
                networkMessage.visit(this.waitress);
            }catch (EOFException eofException){
                this.running = false;
            }catch (Exception e){
                try{
                    socket = new Socket(socket.getInetAddress(), socket.getPort());
                }catch (Exception ex){
                    this.running = false;
                    continue;
                }
            }

        }
    }

}
