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

    private static JLabel lblUsername;
    private static JLabel lblPassword1;
    private static JLabel lblPassword2;
    private static JLabel lblPassword3;
    private static JLabel lblPassword4;
    private static JLabel lblPassword5;
    private static JTextField txtUser;
    private static JPasswordField pass1;
    private static JPasswordField pass2;
    private static JPasswordField pass3;
    private static JPasswordField pass4;
    private static JPasswordField pass5;
    private static JButton bRegister;
    private StopWatch stopWatch = new StopWatch();
    private User user = new User();
    private String username;
    private String password;
    private Hashtable<String, User> usersTable;
    private ArrayList<Long> timings;


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
        lblUsername = new JLabel("Username:");
        lblPassword1 = new JLabel("Password:");
        lblPassword2 = new JLabel("Password:");
        lblPassword3 = new JLabel("Password:");
        lblPassword4 = new JLabel("Password:");
        lblPassword5 = new JLabel("Password:");

        //Setup user fields
        txtUser = new JTextField(15);
        txtUser.setMaximumSize(txtUser.getPreferredSize());
        txtUser.setMinimumSize(txtUser.getPreferredSize());
        txtUser.addKeyListener(this);

        // Password fields used to get a cadence profile
        pass1 = new JPasswordField(15);
        pass1.setMaximumSize(pass1.getPreferredSize());
        pass1.setMinimumSize(pass1.getPreferredSize());
        pass1.addKeyListener(this);

        pass2 = new JPasswordField(15);
        pass2.setMaximumSize(pass2.getPreferredSize());
        pass2.setMinimumSize(pass2.getPreferredSize());
        pass2.addKeyListener(this);

        pass3 = new JPasswordField(15);
        pass3.setMaximumSize(pass3.getPreferredSize());
        pass3.setMinimumSize(pass3.getPreferredSize());
        pass3.addKeyListener(this);

        pass4 = new JPasswordField(15);
        pass4.setMaximumSize(pass4.getPreferredSize());
        pass4.setMinimumSize(pass4.getPreferredSize());
        pass4.addKeyListener(this);

        pass5 = new JPasswordField(15);
        pass5.setMaximumSize(pass5.getPreferredSize());
        pass5.setMinimumSize(pass5.getPreferredSize());
        pass5.addKeyListener(this);

        // add register button
        bRegister = new JButton("Register");
        bRegister.addActionListener(this);

        // UI Alignments
        lblUsername.setAlignmentX(pane.CENTER_ALIGNMENT);
        txtUser.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblPassword1.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblPassword2.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblPassword3.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblPassword4.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblPassword5.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass1.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass2.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass3.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass4.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass5.setAlignmentX(pane.CENTER_ALIGNMENT);
        bRegister.setAlignmentX(pane.CENTER_ALIGNMENT);

        pane.add(txtUser);
        pane.add(lblUsername);
        pane.add(txtUser);
        pane.add(lblPassword1);
        pane.add(pass1);
        pane.add(lblPassword2);
        pane.add(pass2);
        pane.add(lblPassword3);
        pane.add(pass3);
        pane.add(lblPassword4);
        pane.add(pass4);
        pane.add(lblPassword5);
        pane.add(pass5);
        pane.add(bRegister);
    }

    public boolean inputValidation() {
        String pass1Str = pass1.getText();
        String pass2Str = pass2.getText();
        String pass3Str = pass3.getText();
        String pass4Str = pass4.getText();
        String pass5Str = pass5.getText();
        String usernameStr = txtUser.getText();

        // check username
        if (usernameStr.equals("") || usernameStr.equals(null)) {
            return false;
        }

        // check password
        if (areAllEqual(pass1Str, pass2Str, pass3Str, pass4Str, pass5Str)) {
            username = usernameStr;
            password = pass1Str;
            return true;
        } else {
            return false;
        }
    }

    public static boolean areAllEqual(String... values) {
        if (values.length == 0) {
            return true; // Alternative below
        }
        String checkValue = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i].equals(checkValue) && !(values[i].equals(""))) {
                return true;
            } else {
                return false;
            }
        }
        return true;
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
        stopWatch.start();
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
        recordTime();
        // stops, save, reset,start
        // TODO: Multiple stopwatches per field
    }

    /**
     * Records the time to the users profile.
     */
    private void recordTime() {
        stopWatch.stop();
        long time = stopWatch.getTime();
        System.out.println("recorded time is : " + time);
        timings.add(time);
        stopWatch.reset();
        stopWatch.start();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // check all user values are correct
        if (inputValidation()) {
            // Init user object
            user.setPassword(password);
            user.setUsername(username);
            user.setTimings(timings);
            usersTable.put(username, user);

            // Store updated user table
            Storage storage = new Storage(user);
            storage.seralizeUser();
            setVisible(false);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "All passwords must have the same value! Username and Password cannot be NULL or empty!");
            pass1.requestFocus();
        }
    }
}