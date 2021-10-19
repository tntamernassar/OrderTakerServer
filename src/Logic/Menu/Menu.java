package Logic.Menu;


import org.json.simple.JSONObject;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Menu {

    /** Used for Displaying on FE**/
    private List<MenuProduct> menuProductList;

    public Menu(List<MenuProduct> menuProductList){
        this.menuProductList = menuProductList;
    }

    public List<MenuProduct> getMenuProductList() {
        return menuProductList;
    }

    public void addProduct(MenuProduct product){
        this.menuProductList.add(product);
    }

    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        LinkedList<JSONObject> objects = new LinkedList<>();
        for (MenuProduct menuProduct : menuProductList){
            objects.add(menuProduct.toJSON());
        }
        o.put("menuProductList", objects);
        return o;
    }
}
