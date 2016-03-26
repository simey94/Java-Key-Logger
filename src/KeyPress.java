/**
 * Class to represent the Keypress event. Added
 * to an ArrayList to profile user typing cadence.
 */
public class KeyPress {

    char keyPress;
    long timeStamp;

    /**
     * Constructor
     */

    public KeyPress(char keyPress, long timeStamp) {
        this.keyPress = keyPress;
        this.timeStamp = timeStamp;
    }

    /**
     * Getter and Setter
     */

    public char getKeyPress() {
        return keyPress;
    }

    public void setKeyPress(char keyPress) {
        this.keyPress = keyPress;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
