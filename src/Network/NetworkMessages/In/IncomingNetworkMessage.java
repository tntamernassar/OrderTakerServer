package Network.NetworkMessages.In;

import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.NetworkMessage;
import org.json.simple.JSONObject;

public abstract class IncomingNetworkMessage extends NetworkMessage {

    private ConnectionHandler connectionHandler;
    private String SerialNumber;

    public IncomingNetworkMessage(ConnectionHandler connectionHandler, String SerialNumber){
        this.SerialNumber = SerialNumber;
        this.connectionHandler = connectionHandler;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    @Override
    public JSONObject encode() {
        return null;
    }

    public abstract void visit(Waitress waitress);
}
