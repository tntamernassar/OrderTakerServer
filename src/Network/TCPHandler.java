package Network;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class TCPHandler extends Thread{

    private int port;
    private ServerSocket serverSocket;
    private boolean running;
    private boolean connected;

    public TCPHandler(int port){
        this.port = port;
        this.connected = false;
        this.running = true;
    }


    public Socket connect(InetAddress address){
        return connect(address, this.port);
    }

    public Socket connect(InetAddress address, int port){
        try {
            return new Socket(address, port);
        }catch (Exception e){
            System.out.println("Can't connect to " + address.getHostName());
            return null;
        }
    }

    public void stopHandler(){
        this.running = false;
    }

    public abstract void onConnectionRequest(Socket socket);


    @Override
    public void run() {
        //Listen for TCP Connections
        while (this.running){
            try{
                if(!this.connected){
                    this.serverSocket = new ServerSocket(this.port);
                    this.connected = true;
                }
                Socket clientSocket = serverSocket.accept();
                this.onConnectionRequest(clientSocket);
            }catch (Exception e){
                System.out.println("Error while accepting, retrying ...");
                this.connected = false;
                e.printStackTrace();
                break;
            }
        }

        try{
            serverSocket.close();
        }catch (Exception e){
            System.out.println("Error while CLosing TCPHandler socket ...");
        }
    }
}
