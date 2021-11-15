package Logic.Menu;



import Logic.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;

public class MenuProduct implements Product, Serializable {

    private String category;
    private String name;
    private String description;
    private double price;
    private boolean available;
    private String[] images;
    private LinkedList<MenuSection> sections;


    public MenuProduct(String category, String name, String description, double price, boolean available, LinkedList<MenuSection> sections, String[] images){
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.sections = sections;
        this.images = images;
    }

    public MenuProduct(JSONObject menuProduct){
        String category = (String) menuProduct.get("category");
        String name = (String) menuProduct.get("name");
        String description = (String) menuProduct.get("description");
        long price = (long) menuProduct.get("price");
        boolean available = (boolean) menuProduct.get("available");
        JSONArray images = (JSONArray) menuProduct.get("images");
        JSONArray sections = (JSONArray) menuProduct.get("sections");

        this.images = new String[images.size()];
        for(int i=0;i<images.size();i++){
            this.images[i] = (String)images.get(i);
        }

        this.sections = new LinkedList<>();
        for(int i=0;i<sections.size();i++){
            this.sections.add(new MenuSection((JSONObject) sections.get(i)));
        }

        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public LinkedList<MenuSection> getSections() {
        return sections;
    }

    public String[] getImages() {
        return images;
    }

    public String[] getSectionsName(){
        LinkedList<String> res = new LinkedList<>();
        for(MenuSection ms : sections){
            res.add(ms.getSection());
        }
        return (String[]) res.toArray();
    }

    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("category", category);
        o.put("name", name);
        o.put("description", description);
        o.put("price", price);
        o.put("available", available);
        JSONArray objects = new JSONArray();
        if(sections != null) {
            for (MenuSection menuSection : sections) {
                objects.add(menuSection.toJSON());
            }
        }
        o.put("sections", objects);


        JSONArray imagesArray = new JSONArray();
        if(images != null){
            for (int i = 0; i < images.length; i ++){
                imagesArray.add(images[i]);
            }
        }
        o.put("images", imagesArray);


        return o;
    }
    
}
