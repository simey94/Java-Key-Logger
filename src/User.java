import java.util.ArrayList;

/**
 * Class to represent the Keypress event. Added
 * to an ArrayList to profile user typing cadence.
 */

public class User {
    String username;
    // TODO: Encrypt this shit
    String password;
    ArrayList<Long> timings;
}
