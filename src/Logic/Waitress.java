package Logic;


public class Waitress {


    private Restaurant restaurant;
    private String name;


    /**
     *  Waitress class is responsible for the interaction
     *  with the restaurant tables
     *  the functionalities that you can ask a waitress in a restaurant should be here
     * **/
    public Waitress(String name, Restaurant restaurant){
        this.restaurant = restaurant;
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getName() {
        return name;
    }

    public void openTable(int table){}

    public int orderItem(int table, Product product, int quantity, String notes){
        return -1;
    }

    public void removeItem(int table, int orderItem){}

    public void editOrder(int table, int orderItem, Product product, int quantity, String notes){}

    public Order submitOrder(int table){
        return null;
    }

    public Order closeOrder(int table){
        return null;
    }

    public Order cancelOrder(int table){
        return null;
    }

}
