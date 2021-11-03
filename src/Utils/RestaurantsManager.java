package Utils;

import Logic.Waitress;

import java.util.HashMap;

public class RestaurantsManager {

    public static RestaurantsManager instance = new RestaurantsManager();

    public static RestaurantsManager getInstance() {
        return instance;
    }

    private HashMap<String, String> SerialNumberToRestaurantName;
    private HashMap<String, Waitress> RestaurantToWaitress;

    private RestaurantsManager(){
        SerialNumberToRestaurantName = new HashMap<>();
        RestaurantToWaitress = new HashMap<>();
    }

    public void addWaitress(String SerialNumber,String restaurantName, Waitress waitress){
        SerialNumberToRestaurantName.put(SerialNumber, restaurantName);
        RestaurantToWaitress.put(restaurantName, waitress);
    }

    public Waitress getWaitress(String SerialNumber) {
        return RestaurantToWaitress.get(SerialNumberToRestaurantName.get(SerialNumber));
    }
}
