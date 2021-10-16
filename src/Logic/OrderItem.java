package Logic;


import java.io.Serializable;

public class OrderItem implements Serializable {

    private int index;
    private Product product;
    private int quantity;
    private String notes;
    private String waiterName;
    private String timestamp;
    private boolean isDistributed;


    /** Main Constructor **/
    public OrderItem(int index, String waiterName, Product product, int quantity, String notes) {
        this.index = index;
        this.waiterName = waiterName;
        this.product = product;
        this.quantity = quantity;
        this.notes = notes;
        this.isDistributed = false;
    }


    /** Copy Constructor **/
    public OrderItem(int index, String timestamp, String waiterName, Product product, int quantity, String notes, boolean isDistributed) {
        this.index = index;
        this.product = product;
        this.quantity = quantity;
        this.notes = notes;
        this.waiterName = waiterName;
        this.timestamp = timestamp;
        this.isDistributed = isDistributed;
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
}
