package Network.NetworkMessages;

import Network.ConnectionHandler;
import Network.NetworkMessages.In.MenuEdit;
import Network.NetworkMessages.In.TabletImage;
import Network.NetworkMessages.In.initRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class NetworkMessageDecoder {

    public static NetworkMessage decode(ConnectionHandler connectionHandler, JSONObject JSONMessage){

        String SerialNumber = (String)JSONMessage.get("SerialNumber");
        String type = (String)JSONMessage.get("type");

        if(type.equals("TestMessage")){
            return new TestMessage(connectionHandler, SerialNumber);
        }else if(type.equals("initRequest")){
            JSONArray images = (JSONArray) JSONMessage.get("images");
            return new initRequest(connectionHandler, SerialNumber, images);
        }else if(type.equals("MenuEdit")){
            JSONObject menu = (JSONObject) JSONMessage.get("menu");
            JSONArray images = (JSONArray) JSONMessage.get("images");
            return new MenuEdit(connectionHandler, SerialNumber, menu, images);
        }else if(type.equals("TabletImage")){
            String name = (String)JSONMessage.get("name");
            String base64 = (String)JSONMessage.get("base64");
            long chunks = (long)JSONMessage.get("chunks");
            long chunkNumber = (long)JSONMessage.get("chunkNumber");
            return new TabletImage(connectionHandler, SerialNumber, name, base64, chunks, chunkNumber);
        }

        return null;
    }


}
