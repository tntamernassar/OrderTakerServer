package Network.NetworkMessages.Out;

import Logic.Orders.Order;
import Logic.Orders.OrderHistory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class OrderHistoryContainer extends OutGoingNetworkMessage{

    private OrderHistory orderHistory;

    public OrderHistoryContainer(OrderHistory orderHistory){
        this.orderHistory = orderHistory;
    }

    @Override
    public JSONObject encode() {
        JSONObject res = makeJSONMessage("OrderHistoryContainer");
        JSONArray history = new JSONArray();
        for(Order order : orderHistory.getOrders()){
            history.add(order.toJSON());
        }
        res.put("history", history);

        return res;
    }
}
