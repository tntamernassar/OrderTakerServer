package Network.NetworkMessages.Out.Tables;

import Network.NetworkMessages.Out.OutGoingNetworkMessage;
import org.json.simple.JSONObject;

public class CloseTableNotification extends OutGoingNetworkMessage {

    private int table;

    public CloseTableNotification(int table){
        this.table = table;
    }

    @Override
    public JSONObject encode() {
        JSONObject result = makeJSONMessage("CloseTableNotification");

        result.put("table", table);
        return result;
    }

}
