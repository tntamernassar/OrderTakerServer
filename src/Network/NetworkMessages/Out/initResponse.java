package Network.NetworkMessages.Out;

import Logic.Menu.Menu;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;

public class initResponse extends OutGoingNetworkMessage {

    private Menu menu;
    private LinkedList<String> serverImages;

    public initResponse(Menu menu, LinkedList<String> serverImages){
        this.menu = menu;
        this.serverImages = serverImages;
    }

    @Override
    public JSONObject encode() {
        JSONObject menuJSON = menu.toJSON();
        JSONObject result = makeJSONMessage("initResponse");
        JSONArray serverImagesJsonArray = new JSONArray();
        serverImagesJsonArray.addAll(serverImages);


        result.put("menu", menuJSON);
        result.put("serverImages", serverImagesJsonArray);
        return result;
    }

}
