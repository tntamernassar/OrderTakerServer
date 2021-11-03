package Network.NetworkMessages.In;


import Logic.Waitress;
import Network.ConnectionHandler;
import Utils.ImageCollector;
import Utils.ImageManager;
import org.json.simple.JSONObject;

public class TabletImage extends IncomingNetworkMessage {

    private String name;
    private String base64;
    private long chunks;
    private long chunkNumber;

    public TabletImage(ConnectionHandler connectionHandler, String SerialNumber, String name, String base64, long chunks, long chunkNumber) {
        super(connectionHandler, SerialNumber);
        this.name = name;
        this.base64 = base64;
        this.chunks = chunks;
        this.chunkNumber = chunkNumber;
    }


    @Override
    public JSONObject encode() {
        return null;
    }

    @Override
    public void visit(Waitress Waitress) {
        ImageManager.addChunk(Waitress.getRestaurant().getName(), name, base64, (int)chunks, (int)chunkNumber);

        if(chunkNumber == chunks){
            String fullImageBase64 = ImageManager.collectImage(Waitress.getRestaurant().getName(), name);
            boolean success = ImageManager.saveImage(Waitress.getRestaurant().getName(), name, fullImageBase64);
            if(!success){
                System.err.println("Can't save " + name + " in restaurant : " + Waitress.getRestaurant() );
            }
        }
    }
}
