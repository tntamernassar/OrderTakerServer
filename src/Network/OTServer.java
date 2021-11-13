package Network;

import Logic.Menu.Menu;
import Logic.Menu.MenuProduct;
import Logic.Menu.MenuSection;
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

                JSONObject resConfig = (JSONObject) config.get(restaurantName);

                String waitressName = (String) resConfig.get("waitressName");
                JSONArray serialNumbers = (JSONArray) resConfig.get("serialNumbers");
                JSONArray tables = (JSONArray) resConfig.get("tables");

                OrderHistory orderHistory = readOrderHistory(restaurantName);
                if (orderHistory == null){
                    orderHistory = new OrderHistory(new LinkedList<>());
                }
                System.out.println(orderHistory.getOrders().size());
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
            return makeDummyMenu();
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

    private static Menu makeDummyMenu(){
        Menu menu = new Menu(new LinkedList<>());
        LinkedList<MenuSection> sections_example_1 = new LinkedList<>();
        sections_example_1.add(new MenuSection("מידת עשייה", new String[]{"ميديوم", "ويل دان", "دان", "محروق"}, true));
        sections_example_1.add(new MenuSection("نوع اللحم", new String[]{"عجل", "خروف", "دجاج"}, true));
        sections_example_1.add(new MenuSection("نوع الخبز", new String[]{"خبز ابيض", "خبز اسود"}, true));
        sections_example_1.add(new MenuSection("اضافات", new String[]{"خس", "بندورة" ,"خيار", "بصل", "فقع", "جبنه"}, false));

        menu.addProduct(new MenuProduct("لحوم","همبرجر", "لحمه مفرومه   وبعض الوصف  وبعض الوصف", 15, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("لحوم","شنيتسل", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 30, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("لحوم","كباب", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 30, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("لحوم","تورتيا", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 30, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("لحوم","شوارما", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 30, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("لحوم","برجيت", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 30, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("لحوم","صدر دجاج", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 30, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("لحوم","عروسه", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 30, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("لحوم","خروف", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 30, sections_example_1, new String[]{"ham.png"}));

        menu.addProduct(new MenuProduct("اسماك","سلمون", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","شرمبس", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","دينيس", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","لفز", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","سرغوس", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","فريده", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","طرخون", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","لفراك", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","مشط", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","بوري", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("اسماك","سمك", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, sections_example_1, new String[]{"ham.png"}));

        menu.addProduct(new MenuProduct("ايطالي","رفيولي", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("ايطالي","بيتسا", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("ايطالي","باستا", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("ايطالي","طوسط", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("ايطالي","لزانيا", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 100, null, new String[]{"ham.png"}));

        menu.addProduct(new MenuProduct("سلطات","عربيه", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","فتوش", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","ملفوف", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","ذره", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","يونانيه", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","حمص", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","باذنجان", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","بندوره", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","ذره", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("سلطات","جزر", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 20, null, new String[]{"ham.png"}));

        menu.addProduct(new MenuProduct("مشروبات","ماء", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 10, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("مشروبات","كولا", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 10, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("مشروبات","فانتا", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 10, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("مشروبات","سبرايت", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 10, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("مشروبات","توت موز", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 10, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("مشروبات","عنب", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 10, null, new String[]{"ham.png"}));
        menu.addProduct(new MenuProduct("مشروبات","برتقال", "لحمه مفرومه وبعض الوصف لحمه مفرومه وبعض الوصف", 10, null, new String[]{"ham.png"}));

        return menu;
    }
}
