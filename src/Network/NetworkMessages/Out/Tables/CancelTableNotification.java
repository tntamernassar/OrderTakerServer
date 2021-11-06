package Network.NetworkMessages.Out.Tables;

import Network.NetworkMessages.Out.OutGoingNetworkMessage;
import org.json.simple.JSONObject;

public class CancelTableNotification extends OutGoingNetworkMessage {

    private int table;

    public CancelTableNotification(int table){
        this.table = table;
    }

    @Override
    public JSONObject encode() {
        JSONObject result = makeJSONMessage("CancelTableNotification");

        result.put("table", table);
        return result;
    }

}
