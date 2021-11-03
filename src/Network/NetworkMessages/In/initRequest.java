package Network.NetworkMessages.In;


import Logic.Menu.Menu;
import Logic.Waitress;
import Network.ConnectionHandler;
import Network.NetworkMessages.Out.ServerImage;
import Network.NetworkMessages.Out.initResponse;
import Utils.ImageManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;


public class initRequest extends IncomingNetworkMessage {

    private String[] tabletImages;

    public initRequest(ConnectionHandler connectionHandler, String SerialNumber, JSONArray images) {
        super(connectionHandler, SerialNumber);
        this.tabletImages = (String[]) images.toArray(new String[0]);
    }

    @Override
    public JSONObject encode() {
        return null;
    }

    @Override
    public void visit(Waitress waitress) {
        System.out.println("Init request from " + waitress.getName());
        System.out.println("\t - Tablet images : " + Arrays.toString(tabletImages));
        Menu menu = waitress.getRestaurant().getMenu();
        String[] restaurantImages = ImageManager.listImages(waitress.getRestaurant().getName());

        LinkedList<String> clientMissingImages = new LinkedList<>();
        LinkedList<String> serverImages = new LinkedList<>();

        for (String img: restaurantImages){
            serverImages.add(img);
        }

        for (String serverImage : restaurantImages){
            if (!Arrays.asList(this.tabletImages).contains(serverImage)){
                clientMissingImages.add(serverImage);
            }
        }

        this.getConnectionHandler().send(new initResponse(menu, serverImages));

        for (String serverImage : clientMissingImages){
            String base64 = ImageManager.readBase64(waitress.getRestaurant().getName(), serverImage);
            if (base64 != null){
                ImageManager.sendImageInChucks(getConnectionHandler(), serverImage, base64);
            }
        }


    }
}
