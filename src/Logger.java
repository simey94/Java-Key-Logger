import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to monitor number of successful and
 * unsuccessful login attempts for experimentation.
 */
public class Logger {

    public void writeToLog(String username, boolean success) {
        try {
            // true flag to open in append mode
            FileWriter out = new FileWriter("experiment.txt", true);
            if (success) {
                out.write(username + " success" + "\n");
            } else {
                out.write(username + " failure" + "\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
