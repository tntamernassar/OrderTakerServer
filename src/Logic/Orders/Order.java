package Logic.Orders;



import Logic.Product;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Order implements Serializable {

    private int table;
    private AtomicInteger itemsCounter;
    private LocalDateTime startedAt;
    private LocalDateTime closedAt;
    private String startedBy;
    private boolean distributed;
    private Order distributeVersion;
    private HashMap<Integer, OrderItem> orderItems;


    public Order(int table, String startedBy){
        this.table = table;
        this.startedBy = startedBy;
        this.itemsCounter = new AtomicInteger(0);
        this.distributed = false;
        this.distributeVersion = null;
        this.orderItems = new HashMap<>();
        this.startedAt = LocalDateTime.now();
    }

    public Order(JSONObject order){
        this.orderItems = new HashMap<>();

        long table = (long)order.get("table");
        long itemsCounter = (long)order.get("itemsCounter");
        String startedAt = (String)order.get("startedAt");
        String startedBy = (String)order.get("startedBy");
        boolean distributed = (boolean)order.get("distributed");
        Order distributeVersion = null;
        if(order.containsKey("distributeVersion")) {
            distributeVersion = new Order((JSONObject) order.get("distributeVersion"));
        }
        JSONObject orderItems = (JSONObject) order.get("orderItems");
        JSONArray indexes = (JSONArray) orderItems.get("indexes");
        for (int i = 0; i < indexes.size(); i++){
            String indexStr = (String) indexes.get(i);
            int index = Integer.parseInt(indexStr);
            JSONObject orderItemObject = (JSONObject) orderItems.get(indexStr);
            OrderItem orderItem = new OrderItem(orderItemObject, new OrderProduct((JSONObject) orderItemObject.get("product")));
            this.orderItems.put(index, orderItem);
        }

        this.table = (int)table;
        this.itemsCounter = new AtomicInteger((int) itemsCounter);
        this.startedAt = LocalDateTime.parse(startedAt);
        this.startedBy = startedBy;
        this.distributed = distributed;
        this.distributeVersion = distributeVersion;
    }

    public boolean isDistributed() {
        return distributed;
    }

    public void setDistributed(boolean distributed) {
        this.distributed = distributed;
    }

    public void setDistributeVersion(Order distributeVersion) {
        this.distributeVersion = distributeVersion;
    }

    public Order getDistributeVersion() {
        return distributeVersion;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public OrderItem getOrderItem(OrderItem orderItem){
        for (OrderItem o : orderItems.values()){
            if (o.equals(orderItem)){
                return o;
            }
        }

        return null;
    }

    public synchronized void mergerOrder(Order order){
        HashMap<Integer, OrderItem> otherOrderItems = order.getOrderItems();

        for (Integer index : otherOrderItems.keySet()){
            OrderItem otherOrderItem = otherOrderItems.get(index);
            OrderItem serverOrderItem = getOrderItem(otherOrderItem);

            if (serverOrderItem == null){ // new order item
                this.addItem(otherOrderItem.getWaiterName(), otherOrderItem.getTimestamp(), otherOrderItem.getProduct(), otherOrderItem.getQuantity(), otherOrderItem.getNotes(), otherOrderItem.isDistributed(), otherOrderItem.isDeleted());
            } else {
                serverOrderItem.setProduct(otherOrderItem.getProduct());
                serverOrderItem.setQuantity(otherOrderItem.getQuantity());
                serverOrderItem.setDistributed(otherOrderItem.isDistributed());
                serverOrderItem.setNotes(otherOrderItem.getNotes());
                serverOrderItem.setDeleted(otherOrderItem.isDeleted());
            }
        }

        /**
         * Check for items that is in the server but not in the order
         * those items should be deleted
         * */
        for(Integer index : orderItems.keySet()){
            OrderItem serverOrderItem = orderItems.get(index);
            OrderItem otherOrderItem = order.getOrderItem(serverOrderItem);

            if(otherOrderItem == null){ // this item is deleted
                orderItems.remove(index);
            }
        }
    }

    public String getStartedBy() {
        return startedBy;
    }

    public HashMap<Integer, OrderItem> getOrderItems() {
        return orderItems;
    }


    public int addItem(String waiterName, String timestamp, Product product, int quantity, String notes, boolean distributed, boolean deleted){
        int newItemIndex = itemsCounter.incrementAndGet();
        this.orderItems.put(newItemIndex, new OrderItem(newItemIndex, timestamp, waiterName, product, quantity, notes, distributed, deleted));
        return newItemIndex;
    }

    public Order clone() {
        Order o = new Order(table, getStartedBy());
        for(Integer oi: this.orderItems.keySet()){
            OrderItem orderItem = this.orderItems.get(oi);
            o.orderItems.put(oi, new OrderItem(orderItem.getIndex(), orderItem.getTimestamp(), orderItem.getWaiterName(), orderItem.getProduct(), orderItem.getQuantity(), orderItem.getNotes(), orderItem.isDistributed(), orderItem.isDeleted()));
        }
        return o;
    }

    public JSONObject toJSON(){
        try{
            JSONObject res = new JSONObject();
            res.put("table", table);
            res.put("itemsCounter", itemsCounter.get());
            res.put("startedAt", startedAt.toString());
            res.put("closedAt", closedAt == null ? null : closedAt.toString());
            res.put("startedBy", startedBy);
            res.put("distributed", distributed);
            res.put("distributeVersion", distributeVersion != null ? distributeVersion.toJSON() : null);

            JSONObject orderItemsObject = new JSONObject();
            JSONArray indexes = new JSONArray();
            for (Integer index : orderItems.keySet()){
                indexes.add(index.toString());
                orderItemsObject.put(String.valueOf(index), orderItems.get(index).toJSON());
            }
            orderItemsObject.put("indexes", indexes);
            res.put("orderItems", orderItemsObject);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(startedAt, order.startedAt) && Objects.equals(startedBy, order.startedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startedAt, startedBy);
    }
}
