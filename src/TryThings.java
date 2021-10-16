import Network.NetworkMessages.TestMessage;
import org.json.simple.JSONObject;

public class TryThings {

    public static void main(String[] args){


        JSONObject js = new JSONObject();
        js.put("ts", new TestMessage());

        System.out.println(js.toJSONString());

    }

}
