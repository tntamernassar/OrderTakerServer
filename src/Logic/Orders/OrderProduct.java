package Logic.Orders;

import Logic.Menu.MenuProduct;
import Logic.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;

public class OrderProduct implements Product {

    private MenuProduct menuProduct;
    private HashMap<String, LinkedList<String>> addons;

    public OrderProduct(MenuProduct mProduct){
        this.menuProduct = mProduct;
        this.addons = new HashMap<String, LinkedList<String>>();
    }

    public OrderProduct(JSONObject orderProduct){
        this.menuProduct = new MenuProduct((JSONObject) orderProduct.get("menuProduct"));
        this.addons = new HashMap<>();

        JSONObject addons = (JSONObject) orderProduct.get("addons");
        JSONArray sections = (JSONArray) addons.get("sections");
        for(int i=0;i<sections.size();i++){
            String section = (String) sections.get(i);
            JSONArray sectionAddons = (JSONArray) addons.get(section);
            this.addons.put(section, new LinkedList<>(sectionAddons));
        }
    }

    public OrderProduct(MenuProduct mProduct, HashMap<String, LinkedList<String>> addons){
        this.menuProduct = mProduct;
        this.addons = addons;
    }

    public MenuProduct getMenuProduct() {
        return menuProduct;
    }

    public HashMap<String, LinkedList<String>> getAddons() {
        return addons;
    }

    @Override
    public JSONObject toJSON() {
        try{
            JSONObject res = new JSONObject();
            res.put("menuProduct", menuProduct.toJSON());

            JSONObject addonsObject = new JSONObject();
            JSONArray sections = new JSONArray();
            for (String section: addons.keySet()){
                sections.add(section);
                JSONArray addonsList = new JSONArray();
                LinkedList<String> strAddons = addons.get(section);
                for (String s : strAddons){
                    addonsList.add(s);
                }
                addonsObject.put(section, addonsList);
            }
            addonsObject.put("sections", sections);
            res.put("addons", addonsObject);

            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
