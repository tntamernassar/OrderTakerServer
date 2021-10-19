package Network.NetworkMessages;
import org.json.simple.JSONObject;

import java.io.Serializable;

public abstract class NetworkMessage implements Serializable {
    public abstract JSONObject encode();

    public JSONObject makeJSONMessage(String type) {
        JSONObject j = new JSONObject();
        j.put("type", type);
        return j;
    }
}
