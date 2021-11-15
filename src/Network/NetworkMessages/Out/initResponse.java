package Network.NetworkMessages.Out;

import Logic.Menu.Menu;
import Logic.Table;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;

public class initResponse extends OutGoingNetworkMessage {

    private Menu menu;
    private LinkedList<String> serverImages;
    private LinkedList<Table> tables;

    public initResponse(Menu menu, LinkedList<String> serverImages, LinkedList<Table> tables){
        this.menu = menu;
        this.serverImages = serverImages;
        this.tables = tables;
    }

    @Override
    public JSONObject encode() {
        JSONObject menuJSON = menu.toJSON();
        JSONObject result = makeJSONMessage("initResponse");
        JSONArray serverImagesJsonArray = new JSONArray();
        serverImagesJsonArray.addAll(serverImages);

        JSONArray tablesJsonArray = new JSONArray();
        for (Table table : tables){
            tablesJsonArray.add(table.toJSON());
        }


        result.put("menu", menuJSON);
        result.put("serverImages", serverImagesJsonArray);
        result.put("tables", tablesJsonArray);
        System.out.println(tablesJsonArray.get(2).toString());
        return result;
    }

}
