package Network.NetworkMessages.In.Tables;

import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.In.IncomingNetworkMessage;
import Network.NetworkMessages.Out.Tables.OpenTableNotification;

public class OpenTable extends IncomingNetworkMessage {

    private int table;

    public OpenTable(ConnectionHandler connectionHandler, String SerialNumber, int table){
        super(connectionHandler, SerialNumber);
        this.table = table;
    }

    @Override
    public void visit(Waitress waitress) {
        if(!waitress.getRestaurant().getTable(table).isActive()){
            waitress.openTable(table);
            getConnectionHandler().sendToOthers(new OpenTableNotification(table));
        }else {
            System.out.println("Tried to open an opened table");
        }
    }


}
