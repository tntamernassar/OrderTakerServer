package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    private final static String basePath = "/home/tamer/Desktop/OrderTaker/Memory";

    public static String getBasePath() {
        return basePath;
    }

    public static String[] listFiles(String path){
        File directory = new File(path);
        File[] files = directory.listFiles();
        String[] names = new String[files.length];
        int i = 0;
        for(File f : files){
            names[i] = f.getName();
            i++;
        }
        return names;
    }

    public static void deleteFile(String path){
        try{
            Files.delete(Paths.get(path));
        }catch (Exception e){

        }
    }

    public static boolean writeBytes(String path, byte[] bytes){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            fileOutputStream.write(bytes);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeString(String path, String content){
        try{
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
