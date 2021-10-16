import Network.NetworkMessages.TestMessage;
import Utils.Constants;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args){
        try{
            Socket socket = new Socket("localhost",2222);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(Constants.TCP_BUFFER_SIZE);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            TestMessage ts = new TestMessage();
            oos.writeObject(ts.encode());
            byte[] data = baos.toByteArray();
            socket.getOutputStream().write(data);
        }catch(Exception e){e.printStackTrace();}
    }

}
