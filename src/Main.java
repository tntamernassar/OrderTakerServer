import Network.TCPHandler;

import java.net.Socket;

public class Main {

    public static void main(String[] args){
        System.out.println("Hello world");
        TCPHandler tcpHandler = new TCPHandler(443) {
            @Override
            public void onConnectionRequest(Socket socket) {
                System.out.println("Connection");
            }
        };

        tcpHandler.start();
    }

}
