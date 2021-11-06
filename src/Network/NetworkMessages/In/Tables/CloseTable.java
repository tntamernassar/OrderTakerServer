package Network.NetworkMessages.In.Tables;

import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.In.IncomingNetworkMessage;
import Network.NetworkMessages.Out.Tables.CloseTableNotification;
import Network.NetworkMessages.Out.Tables.OpenTableNotification;

public class CloseTable extends IncomingNetworkMessage {

    private int table;

    public CloseTable(ConnectionHandler connectionHandler, String SerialNumber, int table){
        super(connectionHandler, SerialNumber);
        this.table = table;
    }

    @Override
    public void visit(Waitress waitress) {
        if(waitress.getRestaurant().getTable(table).isActive()){
            waitress.closeOrder(table);
            getConnectionHandler().sendToOthers(new CloseTableNotification(table));
        }else {
            System.out.println("Tried to close a closed table");
        }
    }


}
