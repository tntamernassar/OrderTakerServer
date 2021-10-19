package Network.NetworkMessages;

import Network.ConnectionHandler;
import org.json.simple.JSONObject;

public class NetworkMessageDecoder {

    public static NetworkMessage decode(ConnectionHandler connectionHandler, JSONObject JSONMessage){

        String SerialNumber = (String)JSONMessage.get("SerialNumber");
        String type = (String)JSONMessage.get("type");

        if(type.equals("TestMessage")){
            return new TestMessage(connectionHandler, SerialNumber);
        }else if(type.equals("initRequest")){
            return new initRequest(connectionHandler, SerialNumber);
        }

        return null;
    }


}
