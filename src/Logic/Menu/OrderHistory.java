package Logic.Menu;



import Logic.Order;
import Utils.FileManager;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class OrderHistory implements Serializable {

    private HashMap<String, LocalDateTime> lastOrder;
    private HashMap<Integer, CopyOnWriteArrayList<Order>> tablesOrders;


    public OrderHistory(){
        lastOrder = new HashMap<>();
        tablesOrders = new HashMap<>();
    }

    public HashMap<Integer, CopyOnWriteArrayList<Order>> getTablesOrders() {
        return tablesOrders;
    }

    public HashMap<String, LocalDateTime> getLastOrder() {
        return lastOrder;
    }

    private void updateLastOrder(Order newOrder){
        String by = newOrder.getStartedBy();
        if(lastOrder.containsKey(by)){
            LocalDateTime last = lastOrder.get(by);
            if (last.compareTo(newOrder.getStartedAt()) < 0){   // if last < newOrder.startedAt
                lastOrder.put(by, newOrder.getStartedAt());
            }
        }else{
            lastOrder.put(by, newOrder.getStartedAt());
        }
    }

    public boolean addToHistory(int table, Order order){
        if(tablesOrders.containsKey(table)){
            CopyOnWriteArrayList<Order> orders = tablesOrders.get(table);
            for(Order o : orders){
                if(o.getStartedAt().compareTo(order.getStartedAt()) == 0){
                    return false;
                }
            }
            updateLastOrder(order);
            tablesOrders.get(table).add(order);
        }else{
            tablesOrders.put(table, new CopyOnWriteArrayList<Order>(List.of(new Order[]{ order })));
        }
        return true;
    }


    @Override
    public String toString() {
        return "OrderHistory{" +
                "tablesOrders=" + tablesOrders +
                '}';
    }
}
