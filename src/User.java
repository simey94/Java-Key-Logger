import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to represent the Keypress event. Added
 * to an ArrayList to profile user typing cadence.
 */

public class User implements Serializable {
    private String username;
    // TODO: Encrypt this shit
    private String password;
    private ArrayList<Long> timings;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Long> getTimings() {
        return timings;
    }

    public void setTimings(ArrayList<Long> timings) {
        this.timings = timings;
    }


}
