import org.apache.commons.lang3.time.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 * User Registration form.
 */
public class RegisterFrame extends JFrame implements KeyListener, ActionListener {

    private static JLabel lblUsername1;
    private static JLabel lblUsername2;
    private static JLabel lblPassword1;
    private static JLabel lblPassword2;
    private static JTextField txtUser1;
    private static JTextField txtUser2;
    private static JPasswordField pass1;
    private static JPasswordField pass2;
    private static JButton bRegister;
    private StopWatch stopWatchUsername1 = new StopWatch();
    private StopWatch stopWatchUsername2 = new StopWatch();
    private StopWatch stopWatchPassword1 = new StopWatch();
    private StopWatch stopWatchPassword2 = new StopWatch();
    private StopWatch stopWatch = new StopWatch();
    private User user = new User();
    private String username;
    private String password;
    private Storage storage = new Storage(user);
    private Hashtable<String, User> usersTable;
    private ArrayList<Long> timings = new ArrayList<>();
    private ArrayList<Long> username1Timings = new ArrayList<>();
    private ArrayList<Long> username2Timings = new ArrayList<>();
    private ArrayList<Long> password1Timings = new ArrayList<>();
    private ArrayList<Long> password2Timings = new ArrayList<>();
    private boolean usernameError = false;
    private boolean usernameMatchError = false;
    private boolean passwordError = false;
    private boolean passwordMatchError = false;
    int threshold = 300;


    public RegisterFrame(String name, Hashtable usersTable) {
        super(name);
        this.usersTable = usersTable;
        // UI Setup
        setLocation(500, 280);
        setSize(600, 800);
        addComponentsToPane(this.getContentPane());
        pack();
        setVisible(true);
    }

    public void addComponentsToPane(Container pane) {

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        // Set up labels
        lblUsername1 = new JLabel("Username:");
        lblUsername2 = new JLabel("Username:");
        lblPassword1 = new JLabel("Password:");
        lblPassword2 = new JLabel("Password:");

        //Setup user fields
        txtUser1 = new JTextField(15);
        txtUser1.setMaximumSize(txtUser1.getPreferredSize());
        txtUser1.setMinimumSize(txtUser1.getPreferredSize());
        txtUser1.addKeyListener(this);

        //Setup user fields
        txtUser2 = new JTextField(15);
        txtUser2.setMaximumSize(txtUser2.getPreferredSize());
        txtUser2.setMinimumSize(txtUser2.getPreferredSize());
        txtUser2.addKeyListener(this);

        // Password fields used to get a cadence profile
        pass1 = new JPasswordField(15);
        pass1.setMaximumSize(pass1.getPreferredSize());
        pass1.setMinimumSize(pass1.getPreferredSize());
        pass1.addKeyListener(this);

        pass2 = new JPasswordField(15);
        pass2.setMaximumSize(pass2.getPreferredSize());
        pass2.setMinimumSize(pass2.getPreferredSize());
        pass2.addKeyListener(this);

        // add register button
        bRegister = new JButton("Register");
        bRegister.addActionListener(this);

        // UI Alignments
        lblUsername1.setAlignmentX(pane.CENTER_ALIGNMENT);
        txtUser1.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblPassword1.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblUsername2.setAlignmentX(pane.CENTER_ALIGNMENT);
        txtUser1.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblPassword2.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass1.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass2.setAlignmentX(pane.CENTER_ALIGNMENT);
        bRegister.setAlignmentX(pane.CENTER_ALIGNMENT);

        pane.add(lblUsername1);
        pane.add(txtUser1);
        pane.add(lblPassword1);
        pane.add(pass1);
        pane.add(lblUsername2);
        pane.add(txtUser2);
        pane.add(lblPassword2);
        pane.add(pass2);
        pane.add(bRegister);
    }

    public boolean inputValidation() {

        if ((pass1.getPassword().length == 0)) {
            System.out.println("pass1 length 0");
            passwordError = true;
            return false;
        }
        if ((pass2.getPassword().length == 0)) {
            System.out.println("pass2 length 0");
            passwordError = true;
            return false;
        }

        String pass1Str = String.valueOf(pass1.getPassword());
        String pass2Str = String.valueOf(pass2.getPassword());
        String username1Str = txtUser1.getText();
        String username2Str = txtUser2.getText();

        // check username
        if (username1Str.equals("") || username1Str.equals(null)) {
            usernameError = true;
            return false;
        }

        // check username
        if (username2Str.equals("") || username2Str.equals(null)) {
            usernameError = true;
            return false;
        }

        if (!(username1Str.equals(username2Str))) {
            usernameMatchError = true;
        }

        if (pass1Str.equals("") || pass1Str.equals(null)) {
            passwordError = true;
            return false;
        }

        if (pass2Str.equals("") || pass2Str.equals(null)) {
            passwordError = true;
            return false;
        }

        if (!(pass1Str.equals(pass2Str))) {
            passwordMatchError = true;
        }

        // Setup user values
        username = username1Str;
        password = pass1Str;
        return true;
    }

    // TODO: Check that the two arrays are within a threshold of each other then add first to user object
    public boolean entriesWithinThresholdUsername() {
        boolean match = false;
        for (int i = 0; i < username1Timings.size(); i++) {
            for (int j = 0; j < username2Timings.size(); j++) {
                if (Math.abs(username1Timings.get(i) - username2Timings.get(j)) <= threshold) {
                    match = true;
                } else {
                    return false;
                }
            }
        }
        return match;
    }

    private boolean entriesWithinThresholdPassword() {
        boolean match = false;
        for (int i = 0; i < password1Timings.size(); i++) {
            for (int j = 0; j < password2Timings.size(); j++) {
                if (Math.abs(password1Timings.get(i) - password2Timings.get(j)) <= threshold) {
                    match = true;
                } else {
                    return false;
                }
            }
        }
        return match;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // start timing
        // stops, save, reset,start
        int textFieldEntered = 0;

        if (e.getSource() == txtUser1) {
            System.out.println("Key pressed in txtUser1");
            textFieldEntered = 1;
            recordTimeBetweenKeys(textFieldEntered);
        }
        if (e.getSource() == txtUser2) {
            System.out.println("Key pressed in txtUser2");
            textFieldEntered = 2;
            recordTimeBetweenKeys(textFieldEntered);
        }
        if (e.getSource() == pass1) {
            System.out.println("Key pressed in pass1");
            textFieldEntered = 3;
            recordTimeBetweenKeys(textFieldEntered);
        }
        if (e.getSource() == pass2) {
            System.out.println("Key pressed in pass2");
            textFieldEntered = 4;
            recordTimeBetweenKeys(textFieldEntered);
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // stop timing
        int textFieldEntered = 0;
        if (e.getSource() == txtUser1) {
            System.out.println("Key released in txtUser1");
            textFieldEntered = 1;
        }
        if (e.getSource() == txtUser2) {
            System.out.println("Key released in txtUser2");
            textFieldEntered = 2;
        }
        if (e.getSource() == pass1) {
            System.out.println("Key released in pass1");
            textFieldEntered = 3;
        }
        if (e.getSource() == pass2) {
            System.out.println("Key released in pass2");
            textFieldEntered = 4;
        }

        recordTime(textFieldEntered);
    }

    private void recordTimeBetweenKeys(int textFieldEntered) {
        if (textFieldEntered == 0) {
            System.out.println("recordTimeBetweenKeys textFieldEntered was 0 error");
            System.exit(1);
        }
        if (textFieldEntered == 1) {
            if (username1Timings.size() == 0) {
                stopWatchUsername1.start();
            } else {
                long time = stopWatchUsername1.getTime();
                System.out.println("time between key is: " + time);
                username1Timings.add(time);
                stopWatchUsername1.reset();
                stopWatchUsername1.start();
            }
        } else if (textFieldEntered == 2) {
            if (username2Timings.size() == 0) {
                stopWatchUsername2.start();
            } else {
                long time = stopWatchUsername2.getTime();
                System.out.println("time between key is: " + time);
                username2Timings.add(time);
                stopWatchUsername2.reset();
                stopWatchUsername2.start();
            }
        } else if (textFieldEntered == 3) {
            if (password1Timings.size() == 0) {
                stopWatchPassword1.start();
            } else {
                long time = stopWatchPassword1.getTime();
                System.out.println("time between key is: " + time);
                password1Timings.add(time);
                stopWatchPassword1.reset();
                stopWatchPassword1.start();
            }
        } else if (textFieldEntered == 4) {
            if (password2Timings.size() == 0) {
                stopWatchPassword2.start();
            } else {
                long time = stopWatchPassword2.getTime();
                System.out.println("time between key is: " + time);
                password2Timings.add(time);
                stopWatchPassword2.reset();
                stopWatchPassword2.start();
            }
        }
    }

    /**
     * Records the time to the users profile.
     * @param textFieldEntered
     */
    private void recordTime(int textFieldEntered) {
        if (textFieldEntered == 0) {
            System.out.println("textFieldEntered was 0 error");
            System.exit(1);
        }
        if (textFieldEntered == 1) {
            stopWatchUsername1.stop();
            long time = stopWatchUsername1.getTime();
            System.out.println("text 1 key held time is : " + time);
            username1Timings.add(time);
            stopWatchUsername1.reset();
            stopWatchUsername1.start();
        } else if (textFieldEntered == 2) {
            stopWatchUsername2.stop();
            long time = stopWatchUsername2.getTime();
            System.out.println("text2 key held time is : " + time);
            username2Timings.add(time);
            stopWatchUsername2.reset();
            stopWatchUsername2.start();
        } else if (textFieldEntered == 3) {
            stopWatchPassword1.stop();
            long time = stopWatchPassword1.getTime();
            System.out.println("pass1 key held time is : " + time);
            password1Timings.add(time);
            stopWatchPassword1.reset();
            stopWatchPassword1.start();
        } else if (textFieldEntered == 4) {
            stopWatchPassword2.stop();
            long time = stopWatchPassword2.getTime();
            System.out.println("pass2 key held time is : " + time);
            password2Timings.add(time);
            stopWatchPassword2.reset();
            stopWatchPassword2.start();
        }
    }

    private void resetTimings() {
        username1Timings.clear();
        username2Timings.clear();
        password1Timings.clear();
        password2Timings.clear();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // check all user values are correct
        boolean usernameTypingMatch = entriesWithinThresholdUsername();
        boolean passwordTypingMatch = entriesWithinThresholdPassword();

        if (inputValidation() && usernameTypingMatch && passwordTypingMatch) {
            // Init user object
            user.setPassword(password);
            user.setUsername(username);
            for (int i = 0; i < timings.size(); i++) {
                System.out.println("timing value " + i + " " + timings.get(i));
            }
            user.setUsernameTimings(username1Timings);
            user.setPasswordTimings(password1Timings);

            for (int i = 0; i < user.getUsernameTimings().size(); i++) {
                System.out.println("User obj timing value " + i + " " + user.getUsernameTimings().get(i));
            }

            usersTable.put(username, user);
            // Store updated user table
            storage.seralizeUser();
            setVisible(false);
            dispose();
        } else {
            resetTimings();
            if (usernameError) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty or null!");
                txtUser1.requestFocus();
            }
            if (passwordError) {
                JOptionPane.showMessageDialog(null, "Password cannot be NULL or empty!");
                pass1.requestFocus();
            }
            if (!(usernameTypingMatch)) {
                JOptionPane.showMessageDialog(null, "Timings for username typing are not similar enough!");
                txtUser1.requestFocus();
            }
            if (!(passwordTypingMatch)) {
                JOptionPane.showMessageDialog(null, "Timings for password typing are not similar enough!");
                pass1.requestFocus();
            }
            if (usernameMatchError) {
                JOptionPane.showMessageDialog(null, "Usernames must have the same value!");
                pass1.requestFocus();
            }
            if (passwordMatchError) {
                JOptionPane.showMessageDialog(null, "Password must have the same value!");
                pass1.requestFocus();
            }
        }
    }
}