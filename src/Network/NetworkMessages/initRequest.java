package Network.NetworkMessages;


import Logic.Menu.Menu;
import Logic.Waitress;
import Network.ConnectionHandler;
import org.json.simple.JSONObject;

public class initRequest extends IncomingNetworkMessage {


    public initRequest(ConnectionHandler connectionHandler, String SerialNumber) {
        super(connectionHandler, SerialNumber);
    }

    @Override
    public JSONObject encode() {
        return null;
    }

    @Override
    public void visit(Waitress waitress) {
        Menu menu = waitress.getRestaurant().getMenu();
        System.out.println("Sending initResponse to " + this.getSerialNumber());
        this.getConnectionHandler().send(new initResponse(menu));
    }
}
