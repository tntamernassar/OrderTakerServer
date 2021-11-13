package Network.NetworkMessages;

import Network.ConnectionHandler;
import Network.NetworkMessages.In.GetOrderHistory;
import Network.NetworkMessages.In.MenuEdit;
import Network.NetworkMessages.In.Tables.CancelTable;
import Network.NetworkMessages.In.Tables.CloseTable;
import Network.NetworkMessages.In.Tables.OpenTable;
import Network.NetworkMessages.In.Tables.SubmitTable;
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
        }

        /** Menu related messages **/
        else if(type.equals("initRequest")){
            JSONArray images = (JSONArray) JSONMessage.get("images");
            return new initRequest(connectionHandler, SerialNumber, images);
        }else if(type.equals("MenuEdit")){
            JSONObject menu = (JSONObject) JSONMessage.get("menu");
            JSONArray newImages = (JSONArray) JSONMessage.get("newImages");
            JSONArray shouldDeleteImages = (JSONArray) JSONMessage.get("shouldDeleteImages");
            return new MenuEdit(connectionHandler, SerialNumber, menu, newImages, shouldDeleteImages);
        }else if(type.equals("TabletImage")){
            String name = (String)JSONMessage.get("name");
            String base64 = (String)JSONMessage.get("base64");
            long chunks = (long)JSONMessage.get("chunks");
            long chunkNumber = (long)JSONMessage.get("chunkNumber");
            return new TabletImage(connectionHandler, SerialNumber, name, base64, chunks, chunkNumber);
        }

        /** Tables Related Messages **/
        else if(type.equals("OpenTable")){
            long table = (long)JSONMessage.get("table");
            return new OpenTable(connectionHandler, SerialNumber, (int)table);
        }else if(type.equals("CloseTable")){
            long table = (long)JSONMessage.get("table");
            return new CloseTable(connectionHandler, SerialNumber, (int)table);
        }else if(type.equals("CancelTable")){
            long table = (long)JSONMessage.get("table");
            return new CancelTable(connectionHandler, SerialNumber, (int)table);
        }else if(type.equals("SubmitTable")){
            JSONObject table = (JSONObject) JSONMessage.get("table");
            return new SubmitTable(connectionHandler, SerialNumber, table);
        }else if (type.equals("GetOrderHistory")){
            return new GetOrderHistory(connectionHandler, SerialNumber);
        }

        return null;
    }


}
