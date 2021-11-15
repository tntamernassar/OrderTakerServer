package Logic;


import Logic.Orders.Order;

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

    public void openTable(int table){
        Table theTable = restaurant.getTable(table);
        if (!theTable.isActive()){
            theTable.startOrder(table, getName());
            System.out.println("Opened Table " + table);
        }else {

        }
    }



    public Order closeOrder(int table){
        Table theTable = restaurant.getTable(table);
        if(theTable.isActive()){
            Order closedOrder = theTable.closeOrder();
            restaurant.getOrderHistory().tryToAdd(closedOrder);
            boolean success = restaurant.getOrderHistory().write(restaurant.getName() + "/orders");
            if(!success){
                System.err.println("Can't save Order History !");
            }
            System.out.println("Table " + table + " Closed the order");
            return closedOrder;
        }else{
            System.out.println("Table " + table + " Tried to close a closed table");
            return null;
        }
    }

    public Order cancelOrder(int table){
        Table theTable = restaurant.getTable(table);
        if(theTable.isActive()){
            Order closedOrder = theTable.closeOrder();
            System.out.println("Table " + table + " Canceled the order");
            return closedOrder;
        }else{
            System.out.println("Table " + table + " Tried to cancel a closed table");
            return null;
        }
    }

}
