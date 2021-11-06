package Network.NetworkMessages.In.Tables;

import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.In.IncomingNetworkMessage;
import Network.NetworkMessages.Out.Tables.CancelTableNotification;
import Network.NetworkMessages.Out.Tables.CloseTableNotification;

public class CancelTable extends IncomingNetworkMessage {

    private int table;

    public CancelTable(ConnectionHandler connectionHandler, String SerialNumber, int table){
        super(connectionHandler, SerialNumber);
        this.table = table;
    }

    @Override
    public void visit(Waitress waitress) {
        if(waitress.getRestaurant().getTable(table).isActive()){
            waitress.cancelOrder(table);
            getConnectionHandler().sendToOthers(new CancelTableNotification(table));
        }else {
            System.out.println("Tried to cancel a closed table");
        }
    }


}
