package Network.NetworkMessages.In.Tables;

import Logic.Table;
import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.In.IncomingNetworkMessage;
import Network.NetworkMessages.Out.Tables.CancelTableNotification;
import Network.NetworkMessages.Out.Tables.SubmitTableNotification;
import org.json.simple.JSONObject;

public class SubmitTable extends IncomingNetworkMessage {

    private JSONObject table;

    public SubmitTable(ConnectionHandler connectionHandler, String SerialNumber, JSONObject table){
        super(connectionHandler, SerialNumber);
        this.table = table;
    }

    @Override
    public synchronized void visit(Waitress waitress) {
        Table clientTable = new Table(table);
        Table serverTable = waitress.getRestaurant().getTable(clientTable.getNumber());

        System.out.println("Submitted table number " + clientTable.getNumber());
        if(serverTable.isActive()){
            serverTable.getCurrentOrder().setDistributed(serverTable.getCurrentOrder().isDistributed() || clientTable.getCurrentOrder().isDistributed());
            serverTable.getCurrentOrder().setDistributeVersion(clientTable.getCurrentOrder().getDistributeVersion());
            serverTable.mergeTable(clientTable);
        } else {
            serverTable.setTable(clientTable);
        }


        JSONObject serverTableObject = serverTable.toJSON();
        getConnectionHandler().sendToOthers(new SubmitTableNotification(serverTableObject));
    }


}
