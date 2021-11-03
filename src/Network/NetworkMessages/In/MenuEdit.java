package Network.NetworkMessages.In;

import Logic.Menu.Menu;
import Logic.Menu.MenuProduct;
import Logic.Menu.MenuSection;
import Logic.Waitress;
import Network.ConnectionHandler;
import Utils.ImageManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;

public class MenuEdit extends IncomingNetworkMessage{

    private JSONObject menu;
    private JSONArray images;

    public MenuEdit(ConnectionHandler connectionHandler, String SerialNumber, JSONObject menu, JSONArray images) {
        super(connectionHandler, SerialNumber);
        this.images = images;
        this.menu = menu;
    }

    @Override
    public JSONObject encode() {
        return null;
    }


    private LinkedList<MenuProduct> getMenuProducts(){
        try {
            LinkedList<MenuProduct> menuProducts = new LinkedList<>();
            JSONArray menuProductsJson = (JSONArray) menu.get("menuProductList");
            for(int i = 0; i < menuProductsJson.size(); i++){
                JSONObject menuProductJson = (JSONObject)menuProductsJson.get(i);

                String category, name, description;
                double price;
                String[] images = null;
                LinkedList<MenuSection> menuSections = null;

                category = (String) menuProductJson.get("category");
                name = (String) menuProductJson.get("name");
                description = (String) menuProductJson.get("description");
                price = (long) menuProductJson.get("price");

                JSONArray imagesArray = (JSONArray) menuProductJson.get("images");
                if (imagesArray.size() != 0){
                    images = new String[imagesArray.size()];
                    for (int j = 0; j < imagesArray.size(); j++){
                        images[j] = (String)imagesArray.get(j);
                    }
                }


                JSONArray sectionArray = (JSONArray) menuProductJson.get("sections");
                if (sectionArray.size() != 0){
                    menuSections = new LinkedList<>();
                    for (int j = 0; j < sectionArray.size(); j++){
                        JSONObject menuSectionJson = (JSONObject) sectionArray.get(j);
                        String section = (String) menuSectionJson.get("section");
                        boolean maxOne = (boolean) menuSectionJson.get("maxOne");

                        JSONArray addonsArray = (JSONArray) menuSectionJson.get("addons");
                        String[] addons = new String[addonsArray.size()];
                        for (int k = 0; k < addonsArray.size(); k++){
                            addons[k] = (String) addonsArray.get(k);
                        }

                        MenuSection menuSection = new MenuSection(section, addons, maxOne);
                        menuSections.add(menuSection);
                    }
                }

                menuProducts.add(new MenuProduct(category,name, description, price, menuSections, images));
            }
            return menuProducts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void visit(Waitress waitress) {
        // delete unused images
        String[] serverImages = ImageManager.listImages(waitress.getRestaurant().getName());
        String[] clientImages = new String[images.size()];


        for (int i = 0; i < images.size(); i++){
            clientImages[i] = (String) images.get(i);
        }

        System.out.println("MenuEdit request from " + waitress.getName());
        System.out.println("\t - Server images :" + Arrays.toString(serverImages));
        System.out.println("\t - Tablet images :" + Arrays.toString(clientImages));


        for (String serverImage: serverImages){
            if (!Arrays.asList(clientImages).contains(serverImage)){
                ImageManager.deleteImage(waitress.getRestaurant().getName(), serverImage);
            }
        }


        // update menu
        LinkedList<MenuProduct> clientMenuProducts = getMenuProducts();
        waitress.getRestaurant().getMenu().setMenuProductList(clientMenuProducts);
        // save to file
        System.out.println("\tUpdated Menu !");
        // notify connected tablets
    }
}
