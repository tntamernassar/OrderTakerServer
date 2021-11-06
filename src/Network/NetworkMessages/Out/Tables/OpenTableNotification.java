package Network.NetworkMessages.Out.Tables;

import Logic.Menu.Menu;
import Network.NetworkMessages.Out.OutGoingNetworkMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;

public class OpenTableNotification extends OutGoingNetworkMessage {

    private int table;

    public OpenTableNotification(int table){
        this.table = table;
    }

    @Override
    public JSONObject encode() {
        JSONObject result = makeJSONMessage("OpenTableNotification");

        result.put("table", table);
        return result;
    }

}
