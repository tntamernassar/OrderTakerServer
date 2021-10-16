package Network.NetworkMessages;
import Logic.Restaurant;
import Logic.Waitress;
import org.json.simple.JSONObject;

import java.io.Serializable;

public interface NetworkMessage extends Serializable {

    JSONObject encode();

    void visit(Waitress Waitress);

}
