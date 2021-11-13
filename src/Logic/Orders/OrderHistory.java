package Logic.Orders;


import Utils.FileManager;

import java.io.Serializable;
import java.util.LinkedList;

public class OrderHistory implements Serializable {

    private LinkedList<Order> orders;

    public OrderHistory(LinkedList<Order> orders){
        this.orders = orders;
    }


    public void tryToAdd(Order order){
        if(!orders.contains(order)){
            System.out.println(order.toJSON());
            if (order.isDistributed() && !order.getOrderItems().isEmpty()) {
                orders.add(order);
            }
        }
    }

    public LinkedList<Order> getOrders() {
        return orders;
    }

    public boolean write(String filename){
        return FileManager.writeObject(this, filename);
    }
}
