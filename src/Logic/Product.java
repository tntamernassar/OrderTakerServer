package Logic;

import org.json.simple.JSONObject;

import java.io.Serializable;

public interface Product extends Serializable {

    default JSONObject toJSON(){
        return null;
    }

}
