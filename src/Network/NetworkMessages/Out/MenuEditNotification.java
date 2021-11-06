package Network.NetworkMessages.Out;

import Logic.Menu.Menu;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MenuEditNotification extends OutGoingNetworkMessage{

    private Menu menu;
    private List<String> newImages;
    private List<String> shouldDelete;

    public MenuEditNotification(Menu menu, List<String> newImages, List<String> shouldDelete){
        this.menu = menu;
        this.newImages = newImages;
        this.shouldDelete = shouldDelete;
    }

    @Override
    public JSONObject encode() {
        JSONObject res = makeJSONMessage("MenuEditNotification");
        JSONArray newImagesArray = new JSONArray();
        JSONArray shouldDeleteArray = new JSONArray();

        for(String newImg: newImages){ newImagesArray.add(newImg); }
        for(String toDeleteImg: shouldDelete){ shouldDeleteArray.add(toDeleteImg); }

        JSONObject menuObject = menu.toJSON();

        res.put("menu", menuObject);
        res.put("newImages", newImagesArray);
        res.put("shouldDelete", shouldDeleteArray);

        return res;
    }
}
