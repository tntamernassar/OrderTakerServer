package Network.NetworkMessages;

import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.In.IncomingNetworkMessage;
import org.json.simple.JSONObject;

public class TestMessage extends IncomingNetworkMessage {

    public TestMessage(ConnectionHandler connectionHandler, String restaurantName){
        super(connectionHandler, restaurantName);
    }

    @Override
    public JSONObject encode() {
        return makeJSONMessage("TestMessage");
    }

    @Override
    public void visit(Waitress waitress) {}
}
