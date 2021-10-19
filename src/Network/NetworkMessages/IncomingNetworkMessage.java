package Network.NetworkMessages;

import Logic.Waitress;
import Network.ConnectionHandler;
import org.json.simple.JSONObject;

public abstract class IncomingNetworkMessage extends NetworkMessage{

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
    public abstract JSONObject encode();

    public abstract void visit(Waitress waitress);
}
