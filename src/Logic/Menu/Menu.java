package Logic.Menu;


import Utils.FileManager;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Menu implements Serializable {

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

    public void setMenuProductList(List<MenuProduct> menuProductList) {
        this.menuProductList = new LinkedList<>();
        for (MenuProduct menuProduct : menuProductList){
            addProduct(menuProduct);
        }
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
