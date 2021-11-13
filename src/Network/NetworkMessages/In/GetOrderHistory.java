package Network.NetworkMessages.In;

import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.Out.OrderHistoryContainer;

public class GetOrderHistory extends IncomingNetworkMessage{

    public GetOrderHistory(ConnectionHandler connectionHandler, String SerialNumber) {
        super(connectionHandler, SerialNumber);
    }

    @Override
    public void visit(Waitress waitress) {
        System.out.println("Order History Request");
        getConnectionHandler().send(new OrderHistoryContainer(waitress.getRestaurant().getOrderHistory()));
    }
}
