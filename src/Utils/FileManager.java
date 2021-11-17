package Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private static String basePath;


    public static void init(String _basePath){
        basePath = _basePath;
    }

    public static String getBasePath() {
        return basePath;
    }

    public static void mkdir(String name){
        File file = new File(getBasePath() + "/" + name);
        if (!file.exists()){
            file.mkdir();
        }
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

    public static String readFile(String name){
        try {
            Path fileName = Path.of(basePath + "/" + name);
            String s = Files.readString(fileName);
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return null;
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

    public synchronized static boolean writeObject(Serializable object, String filename){
        try {
            File file = new File(basePath + "/" + filename);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(object);
            os.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object readObject(String filename){
        try {
            File file = new File(basePath+ "/" + filename);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            Object o =  is.readObject();
            is.close();
            fis.close();
            return o;
        } catch (Exception e) {
            return null;
        }
    }


}
