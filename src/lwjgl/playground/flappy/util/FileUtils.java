package lwjgl.playground.flappy.util;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Kyle Flynn on 8/30/2016.
 */
public class FileUtils {

    private FileUtils() {

    }

    public static String loadAsString(String file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader((new FileReader(file)));
            String buffer = "";

            while ((buffer = reader.readLine()) != null) {
                result.append(buffer + "\n");
            }
            reader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
