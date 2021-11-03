package Utils;

import Network.ConnectionHandler;
import Network.NetworkMessages.Out.ServerImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public class ImageManager {


    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ImageCollector>> imageCollectorHashMap = new ConcurrentHashMap<>();

    public static void sendImageInChucks(ConnectionHandler connectionHandler, String name, String base64){
        int chunkSize = (int)Constants.TCP_CHUNK_LENGTH;
        int chunksNeeded =  (int)Math.ceil(base64.length() / chunkSize);

        for(int i = 0; i < chunksNeeded; i++){
            int chunkNumber = i + 1;
            String chunk = base64.substring(i * chunkSize, Math.min(i*chunkSize + chunkSize, base64.length()));
            connectionHandler.send(new ServerImage(name, chunk, chunksNeeded, chunkNumber));
        }

    }

    public static void addChunk(String restaurantName, String fileName, String base64, int chunks, int chunkNumber){
        if (!imageCollectorHashMap.containsKey(restaurantName)){
            imageCollectorHashMap.put(restaurantName, new ConcurrentHashMap<>());
        }

        if (!imageCollectorHashMap.get(restaurantName).containsKey(fileName)){
            imageCollectorHashMap.get(restaurantName).put(fileName, new ImageCollector(chunks));
        }

        imageCollectorHashMap.get(restaurantName).get(fileName).addChunk(chunkNumber, base64);
    }

    public static String collectImage(String restaurantName, String fileName){
        String s = imageCollectorHashMap.get(restaurantName).get(fileName).collectImage();
        imageCollectorHashMap.get(restaurantName).get(fileName).restart();
        return s;
    }

    public static String[] listImages(String restaurant){
        String path = FileManager.getBasePath() + "/" + restaurant + "/images/";
        return FileManager.listFiles(path);
    }

    public static String readBase64(String restaurant, String name){
        try {
            String image_path = FileManager.getBasePath() + "/" + restaurant + "/images/" + name;
            File file = new File(image_path);
            byte[] content = Files.readAllBytes(file.toPath());
            String base64 = new String(Base64.getEncoder().encode(content), "UTF-8");
            return base64;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveImage(String restaurant, String name, String base64){
        byte[] bytes = Base64.getDecoder().decode(base64);
        String image_path = FileManager.getBasePath() + "/" + restaurant + "/images/" + name;
        return FileManager.writeBytes(image_path, bytes);
    }

    public static void deleteImage(String restaurant, String name){
        String image_path = FileManager.getBasePath() + "/" + restaurant + "/images/" + name;
        FileManager.deleteFile(image_path);
    }






}
