package Network.NetworkMessages;

import org.json.simple.JSONObject;

public class NetworkMessageDecoder {

    public static NetworkMessage decode(JSONObject JSONMessage){

        String type = (String)JSONMessage.get("type");

        if(type.equals("TestMessage")){
            return new TestMessage();
        }

        return null;
    }


}
