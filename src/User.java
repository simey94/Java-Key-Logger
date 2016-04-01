import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to represent the Keypress event. Added
 * to an ArrayList to profile user typing cadence.
 */

public class User implements Serializable {
    // Required for serilization
    private static final long serialVersionUID = 2939771466342253014L;
    private String username;
    private String password;
    private ArrayList<Long> usernameTimings;
    private ArrayList<Long> passwordTimings;

    public User(String username, String password, ArrayList<Long> usernameTimings, Long averageCadence) {
        this.username = username;
        this.password = password;
        this.usernameTimings = usernameTimings;
    }

    public User() {

    }

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

    public ArrayList<Long> getUsernameTimings() {
        return usernameTimings;
    }

    public void setUsernameTimings(ArrayList<Long> usernameTimings) {
        this.usernameTimings = usernameTimings;
    }

    public ArrayList<Long> getPasswordTimings() {
        return passwordTimings;
    }

    public void setPasswordTimings(ArrayList<Long> passwordTimings) {
        this.passwordTimings = passwordTimings;
    }

}
