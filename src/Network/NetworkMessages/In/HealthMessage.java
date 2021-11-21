package Network.NetworkMessages.In;

import Logic.Waitress;
import Network.ConnectionHandler;

public class HealthMessage extends IncomingNetworkMessage{


    public HealthMessage(ConnectionHandler connectionHandler, String SerialNumber) {
        super(connectionHandler, SerialNumber);
    }

    @Override
    public void visit(Waitress waitress) {
    }
}
