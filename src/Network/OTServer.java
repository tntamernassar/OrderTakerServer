package Network;

import Logic.Restaurant;
import Logic.Waitress;
import Utils.Constants;

import java.net.Socket;

public class OTServer {

    public static void main(String[] args){
        System.out.println("OrderTaker Server Started");

        Restaurant restaurant = new Restaurant();
        Waitress waitress = new Waitress("ServerWaitress", restaurant);

        TCPHandler tcpHandler = new TCPHandler(Constants.TCP_PORT) {
            @Override
            public void onConnectionRequest(Socket socket) {
                System.out.println("Connection accepted");
                ConnectionHandler c = new ConnectionHandler(socket, waitress);
                c.start();
            }
        };

        tcpHandler.start();
    }

}
