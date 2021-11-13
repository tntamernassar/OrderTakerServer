package Logic.Orders;


import Logic.Product;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.Objects;

public class OrderItem implements Serializable {

    private int index;
    private String timestamp;
    private String waiterName;

    private Product product;
    private int quantity;
    private String notes;
    private boolean isDistributed;
    private boolean deleted;


    /** Main Constructor **/
    public OrderItem(int index, String waiterName, Product product, int quantity, String notes, boolean deleted) {
        this.index = index;
        this.waiterName = waiterName;
        this.product = product;
        this.quantity = quantity;
        this.notes = notes;
        this.isDistributed = false;
    }


    /** Copy Constructor **/
    public OrderItem(int index, String timestamp, String waiterName, Product product, int quantity, String notes, boolean isDistributed, boolean deleted) {
        this.index = index;
        this.product = product;
        this.quantity = quantity;
        this.notes = notes;
        this.waiterName = waiterName;
        this.timestamp = timestamp;
        this.isDistributed = isDistributed;
    }

    public OrderItem(JSONObject orderItem, Product product){
        long index = (long)orderItem.get("index");
        long quantity = (long)orderItem.get("quantity");
        String notes = (String) orderItem.get("notes");
        String waiterName = (String) orderItem.get("waiterName");
        String timestamp = (String) orderItem.get("timestamp");
        boolean isDistributed = (boolean) orderItem.get("isDistributed");
        boolean deleted = (boolean) orderItem.get("deleted");

        this.index = (int)index;
        this.product = product;
        this.quantity = (int)quantity;
        this.notes = notes;
        this.waiterName = waiterName;
        this.timestamp = timestamp;
        this.isDistributed = isDistributed;
        this.deleted = deleted;
    }



    public int getIndex() {
        return index;
    }

    public boolean isDistributed() {
        return isDistributed;
    }

    public void setDistributed(boolean distributed) {
        isDistributed = distributed;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "OrderItem {" + "\n\t" +
                "waiter = " + waiterName +"\n\t" +
                "timestamp = " + timestamp +"\n\t" +
                "distributed = " + isDistributed +"\n\t" +
                "product = " + product +"\n\t" +
                "quantity = " + quantity +"\n\t" +
                "notes = " + notes  +"\n" +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return waiterName.equals(orderItem.waiterName) && timestamp.equals(orderItem.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(waiterName, timestamp);
    }

    public JSONObject toJSON(){
        try{
            JSONObject res = new JSONObject();
            res.put("index", index);
            res.put("product", product.toJSON());
            res.put("quantity", quantity);
            res.put("notes", notes);
            res.put("waiterName", waiterName);
            res.put("timestamp", timestamp);
            res.put("isDistributed", isDistributed);
            res.put("deleted", deleted);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
