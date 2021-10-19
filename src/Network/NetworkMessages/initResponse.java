package Network.NetworkMessages;

import Logic.Menu.Menu;
import org.json.simple.JSONObject;

public class initResponse extends OutGoingNetworkMessage {

    private Menu menu;

    public initResponse(Menu menu){
        this.menu = menu;
    }

    @Override
    public JSONObject encode() {
        JSONObject menuJSON = menu.toJSON();
        JSONObject result = makeJSONMessage("initResponse");
        result.put("menu", menuJSON);
        return result;
    }

}
