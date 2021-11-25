package Network;

import Logic.Menu.Menu;
import Logic.Orders.OrderHistory;
import Logic.Restaurant;
import Logic.Table;
import Logic.Waitress;
import Utils.FileManager;
import Utils.RestaurantsManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.Socket;
import java.util.LinkedList;

public class OTServer {

    public static void main(String[] args){
        int port = Integer.parseInt(args[0]);
        String basePath = args[1];
        System.out.println("OrderTaker Server Started on port " + port);
        FileManager.init(basePath);
        initSystem();
        KnownConnections knownConnections = new KnownConnections();
        TCPHandler tcpHandler = new TCPHandler(port) {
            @Override
            public void onConnectionRequest(Socket socket) {
                System.out.println("Connection accepted");
                ConnectionHandler c = new ConnectionHandler(socket, knownConnections);
                c.start();
            }
        };

        tcpHandler.start();
    }

    private static void initSystem(){
        String s = FileManager.readFile("config");
        JSONParser parser = new JSONParser();
        RestaurantsManager restaurantsManager = RestaurantsManager.getInstance();
        try{
            JSONObject config = (JSONObject) parser.parse(s);
            JSONArray restaurants = (JSONArray) config.get("restaurants");
            for (int i = 0; i < restaurants.size(); i++){
                String restaurantName = (String) restaurants.get(i);
                FileManager.mkdir(restaurantName);
                FileManager.mkdir(restaurantName + "/images");
                JSONObject resConfig = (JSONObject) config.get(restaurantName);

                String waitressName = (String) resConfig.get("waitressName");
                JSONArray serialNumbers = (JSONArray) resConfig.get("serialNumbers");
                JSONArray tables = (JSONArray) resConfig.get("tables");

                OrderHistory orderHistory = readOrderHistory(restaurantName);
                if (orderHistory == null){
                    orderHistory = new OrderHistory(new LinkedList<>());
                }
                Restaurant restaurant = new Restaurant(restaurantName, readMenu(restaurantName), orderHistory);
                Waitress waitress = new Waitress(waitressName, restaurant);

                for (int j = 0; j < tables.size(); j++){
                    long table = (long) tables.get(j);
                    restaurant.addTable(new Table((int)table));
                }

                for (int j = 0; j < serialNumbers.size(); j++){
                    String serialNumber = (String) serialNumbers.get(j);
                    restaurantsManager.addWaitress(serialNumber, restaurantName, waitress);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Menu readMenu(String restaurant){
        Object o = FileManager.readObject(restaurant+"/menu");
        if(o == null){
            return makeEmptyMenu();
        }else{
            return (Menu) o;
        }
    }

    private static OrderHistory readOrderHistory(String restaurant){
        Object o = FileManager.readObject(restaurant+"/orders");
        if(o == null){
            return null;
        }else{
            return (OrderHistory) o;
        }
    }

    private static Menu makeEmptyMenu(){
        Menu menu = new Menu(new LinkedList<>());
        return menu;
    }
}
