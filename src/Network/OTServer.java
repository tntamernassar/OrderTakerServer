package Network;

import Logic.Menu.Menu;
import Logic.Menu.MenuProduct;
import Logic.Menu.MenuSection;
import Logic.Restaurant;
import Logic.Waitress;

import java.net.Socket;
import java.util.LinkedList;

public class OTServer {

    public static void main(String[] args){
        System.out.println("OrderTaker Server Started");

        Restaurant restaurant = new Restaurant("res1", makeDummyMenu());
        Waitress waitress = new Waitress("res1Waitress", restaurant);

        RestaurantsManager.getInstance().addWaitress("John", restaurant.getName(), waitress);

        TCPHandler tcpHandler = new TCPHandler(2222) {
            @Override
            public void onConnectionRequest(Socket socket) {
                System.out.println("Connection accepted");
                ConnectionHandler c = new ConnectionHandler(socket);
                c.start();
            }
        };

        tcpHandler.start();
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
