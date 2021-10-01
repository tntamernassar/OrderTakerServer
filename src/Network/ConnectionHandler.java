package Network;


import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.Socket;

public abstract class ConnectionHandler extends Thread{

    protected Socket socket;
    private boolean running;

    public ConnectionHandler(Socket socket){
        this.socket = socket;
        this.running = true;
    }


    public boolean send(String s){
        try {
            byte[] data = s.getBytes();
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
                Object networkNotification =  ois.readObject();
                System.out.println(networkNotification + " <<<<<<< From network");
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

    public boolean isReachable(){
        try{
            return this.running && socket.getInetAddress().isReachable(1);
        }catch (Exception e){
            return false;
        }
    }


}
