import java.io.FileWriter;
import java.io.IOException;

/**
 * Class to monitor number of successful and
 * unsuccessful login attempts for experimentation.
 */
public class Logger {

    private int successfulLoginAttempts;
    private int failedLoginAttempts;

    public void incrementSuccessfulLoginAttempts() {
        successfulLoginAttempts++;
    }

    public void incrementFailedLoginAttempts() {
        failedLoginAttempts++;
    }

    public void writeToLog() {
        try {
            // true flag to open in append mode
            System.out.printf("LOL");
            FileWriter out = new FileWriter("experiment.txt", true);
            for (int i = 0; i < successfulLoginAttempts; i++) {
                System.out.printf("SUCCESS");
                out.write("success" + "\n");
            }
            for (int j = 0; j < failedLoginAttempts; j++) {
                System.out.printf("FAIL");
                out.write("failure" + "\n");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
