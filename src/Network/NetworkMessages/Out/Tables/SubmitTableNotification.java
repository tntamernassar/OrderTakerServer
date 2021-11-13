package Network.NetworkMessages.Out.Tables;

import Network.NetworkMessages.Out.OutGoingNetworkMessage;
import org.json.simple.JSONObject;

public class SubmitTableNotification extends OutGoingNetworkMessage {

    private JSONObject table;

    public SubmitTableNotification(JSONObject table){
        this.table = table;
    }

    @Override
    public JSONObject encode() {
        JSONObject result = makeJSONMessage("SubmitTableNotification");

        result.put("table", table);
        return result;
    }

}
