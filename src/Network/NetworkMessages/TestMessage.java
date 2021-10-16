package Network.NetworkMessages;

import Logic.Waitress;
import org.json.simple.JSONObject;

public class TestMessage implements NetworkMessage{

    @Override
    public JSONObject encode() {
        JSONObject j = new JSONObject();
        j.put("type", "TestMessage");
        return j;
    }

    @Override
    public void visit(Waitress waitress) {
        System.out.println("Visiting res");
    }
}
