import java.io.DataOutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args){
        try{
            Socket s = new Socket("193.123.85.54",443);
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            dout.writeUTF("Hello Server");
            dout.flush();
            dout.close();
            s.close();
        }catch(Exception e){System.out.println(e);}
    }

}
