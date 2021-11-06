package Logic;

import Logic.Menu.Menu;
import Logic.Menu.OrderHistory;

import java.io.Serializable;
import java.util.HashMap;

public class Restaurant implements Serializable {

    private String name;
    private Menu menu;
    private HashMap<Integer, Table> tables;
    private OrderHistory orderHistory;

    public Restaurant(String name,  Menu menu){
        this.tables = new HashMap<>();
        this.menu = menu;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Menu getMenu() {
        return menu;
    }

    public OrderHistory getOrderHistory() {
        return orderHistory;
    }

    public int[] getTables() {
        int [] res = new int[tables.size()];
        int index = 0;
        for (Integer i : tables.keySet()){
            res[index++] = i;
        }
        return res;
    }

    public Table getTable(int number) {
        return tables.get(number);
    }



    public void addTable(Table table) {
        this.tables.put(table.getNumber(), table);
    }

}
