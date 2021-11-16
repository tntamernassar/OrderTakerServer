import Network.NetworkMessages.TestMessage;
import org.json.simple.JSONObject;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TryThings {

    public static void main(String[] args){

//        generateString(10);

        int i = ThreadLocalRandom.current().nextInt(0, 2);
        System.out.println(i);
    }



    public static String generateString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

}
