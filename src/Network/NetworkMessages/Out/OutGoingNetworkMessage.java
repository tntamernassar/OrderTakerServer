package Network.NetworkMessages.Out;


import Network.NetworkMessages.NetworkMessage;
import org.json.simple.JSONObject;

public abstract class OutGoingNetworkMessage extends NetworkMessage {

    @Override
    public abstract JSONObject encode();

}
