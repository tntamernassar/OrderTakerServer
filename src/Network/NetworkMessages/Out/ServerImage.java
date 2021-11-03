package Network.NetworkMessages.Out;

import org.json.simple.JSONObject;

public class ServerImage extends OutGoingNetworkMessage {

    private String name;
    private String base64;
    private int chunks;
    private int chunkNumber;

    public ServerImage(String name, String base64, int chunks, int chunkNumber){
        this.name = name;
        this.base64 = base64;
        this.chunks = chunks;
        this.chunkNumber = chunkNumber;
    }

    @Override
    public JSONObject encode() {
        JSONObject result = makeJSONMessage("ServerImage");

        result.put("name", name);
        result.put("base64", base64);
        result.put("chunks", chunks);
        result.put("chunkNumber", chunkNumber);
        return result;
    }

}
