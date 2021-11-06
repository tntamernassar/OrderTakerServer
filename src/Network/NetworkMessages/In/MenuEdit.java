package Network.NetworkMessages.In;

import Logic.Menu.MenuProduct;
import Logic.Menu.MenuSection;
import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.Out.MenuEditNotification;
import Utils.FileManager;
import Utils.ImageManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MenuEdit extends IncomingNetworkMessage{

    private JSONObject menu;
    private JSONArray newImagesArray;
    private JSONArray shouldDeleteImagesArray;

    public MenuEdit(ConnectionHandler connectionHandler, String SerialNumber, JSONObject menu, JSONArray newImagesArray, JSONArray shouldDeleteImagesArray) {
        super(connectionHandler, SerialNumber);
        this.menu = menu;
        this.newImagesArray = newImagesArray;
        this.shouldDeleteImagesArray = shouldDeleteImagesArray;
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
        List<String> newImages = new LinkedList<>();
        List<String> shouldDelete = new LinkedList<>();

        for (int i=0;i<newImagesArray.size();i++){
            newImages.add((String) newImagesArray.get(i));
        }
        for (int i=0;i<shouldDeleteImagesArray.size();i++){
            shouldDelete.add((String) shouldDeleteImagesArray.get(i));
        }

        System.out.println("MenuEdit request from " + waitress.getName());
        System.out.println("\t - New images :" + Arrays.toString(newImages.toArray()));
        System.out.println("\t - To Delete images :" + Arrays.toString(shouldDelete.toArray()));

        // update menu
        LinkedList<MenuProduct> clientMenuProducts = getMenuProducts();
        waitress.getRestaurant().getMenu().setMenuProductList(clientMenuProducts);

        // save to file
        FileManager.writeObject(waitress.getRestaurant().getMenu(), waitress.getRestaurant().getName() + "/menu");
        System.out.println("\tUpdated Menu !");

        // notify connected tablets
        for (String img: newImages){
            String base64 = ImageManager.readBase64(waitress.getRestaurant().getName(), img);
            ImageManager.sendImageInChucksToOthers(getConnectionHandler(), img, base64);
        }
        getConnectionHandler().sendToOthers(new MenuEditNotification(waitress.getRestaurant().getMenu(), newImages, shouldDelete));

        // delete extra images
        for (String serverImage: shouldDelete) {
            ImageManager.deleteImage(waitress.getRestaurant().getName(), serverImage);
        }

    }
}
