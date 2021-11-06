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

    public void openTable(int table){
        Table theTable = restaurant.getTable(table);
        if (!theTable.isActive()){
            theTable.startOrder(getName());
            System.out.println("Opened Table " + table);
        }else {
        }
    }

    public int orderItem(int table, Product product, int quantity, String notes) {
        if (!this.restaurant.getTable(table).isActive()) {
            this.restaurant.getTable(table).startOrder(getName());
        }
        return this.restaurant.getTable(table).getCurrentOrder().addItem(this.getName(), product, quantity, notes);
    }

    public void removeItem(int table, int orderItem){
        Table theTable = restaurant.getTable(table);
        if(theTable.isActive()){
            theTable.getCurrentOrder().removeItem(orderItem);
        }
    }

    public void editOrder(int table, int orderItem, Product product, int quantity, String notes){
        restaurant.getTable(table).getCurrentOrder().editOrder(orderItem, product, quantity, notes);
    }

    public Order submitOrder(int table){
        if(restaurant.getTable(table).isActive()){
            Table theTable = restaurant.getTable(table);
            Order currentOrder = theTable.getCurrentOrder();
            if(currentOrder.getOrderItems().size() > 0){
                currentOrder.distributeItems();
                if(currentOrder.isDistributed()){
                    // the order already distributed
                    currentOrder.setDistributeVersion(currentOrder.clone());
                    System.out.println("Table " + table + " ReSubmitted an order");
                }else{
                    // this is the first order from this table
                    currentOrder.setDistributed(true);
                    currentOrder.setDistributeVersion(currentOrder.clone());
                    System.out.println("Table " + table + " Submitted the order");
                }
                return currentOrder;
            }else{
                System.out.println("Order contains no item");
                return null;
            }
        }else{
            System.out.println("Table " + table + " Tried to submit a closed table !");
            return null;
        }
    }

    public Order closeOrder(int table){
        Table theTable = restaurant.getTable(table);
        if(theTable.isActive()){
            if(theTable.getCurrentOrder().getOrderItems().size() > 0){
                boolean added = this.restaurant.getOrderHistory().addToHistory(table, theTable.getCurrentOrder());
                if(added) {
//                    this.restaurant.getOrderHistory().write();
                }
            }
            Order closedOrder = theTable.closeOrder();
            System.out.println("Table " + table + " Closed the order");
            return closedOrder;
        }else{
            System.out.println("Table " + table + " Tried to close a closed table");
            return null;
        }
    }

    public Order cancelOrder(int table){
        return null;
    }

}
