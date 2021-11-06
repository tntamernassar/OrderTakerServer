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
    private String[] images;
    private LinkedList<MenuSection> sections;


    public MenuProduct(String category, String name, String description, double price, LinkedList<MenuSection> sections, String[] images){
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sections = sections;
        this.images = images;

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
