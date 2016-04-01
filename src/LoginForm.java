import org.apache.commons.lang3.time.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class LoginForm extends JFrame implements KeyListener, ActionListener {

    private static JLabel lblUsername;
    private static JLabel lblPassword;
    private static JButton bLogin;
    private static JButton button;
    private static JButton bRegister;
    private static JTextField txtUser;
    private static JPasswordField pass;
    private static JTextArea logArea;
    private StopWatch stopWatchUsername = new StopWatch();
    private StopWatch stopWatchPassword = new StopWatch();
    private ArrayList<Long> usernameTimings = new ArrayList<>();
    private ArrayList<Long> passwordTimings = new ArrayList<>();
    private Logger logger = new Logger();
    private Hashtable<String, User> usersTable = new Hashtable<>();
    private Storage storage = new Storage();
    static final String newline = System.getProperty("line.separator");
    private int threshold = 300;

    /**
     * Constructor
     */

    public LoginForm(String name){
        super(name);
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        LoginForm frame = new LoginForm("Login Authentication");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Setup of UI elements.
     */
    private void addComponentsToPane(Container pane) {

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        // TODO: Add CLI lib
        // Setup Buttons
        button = new JButton("Clear");
        button.addActionListener(this);
        bLogin = new JButton("Login");
        bLogin.addActionListener(this);
        bRegister = new JButton("Register");
        bRegister.addActionListener(this);

        // Set up labels
        lblUsername = new JLabel("Username:");
        lblPassword = new JLabel("Password:");

        // Set up text fields
        txtUser = new JTextField(15);
        txtUser.setMaximumSize(txtUser.getPreferredSize());
        txtUser.setMinimumSize(txtUser.getPreferredSize());
        txtUser.addKeyListener(this);

        pass = new JPasswordField(15);
        pass.setMaximumSize(pass.getPreferredSize());
        pass.setMinimumSize(pass.getPreferredSize());
        pass.addKeyListener(this);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(375, 125));

        lblUsername.setAlignmentX(pane.CENTER_ALIGNMENT);
        txtUser.setAlignmentX(pane.CENTER_ALIGNMENT);
        lblPassword.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass.setAlignmentX(pane.CENTER_ALIGNMENT);
        scrollPane.setAlignmentX(pane.CENTER_ALIGNMENT);
        bLogin.setAlignmentX(pane.CENTER_ALIGNMENT);
        button.setAlignmentX(pane.CENTER_ALIGNMENT);
        bRegister.setAlignmentX(pane.CENTER_ALIGNMENT);

        pane.add(lblUsername);
        pane.add(txtUser);
        pane.add(lblPassword);
        pane.add(pass);
        pane.add(scrollPane);
        pane.add(bLogin);
        pane.add(button);
        pane.add(bRegister);
        actionLogin();
        registerUser();
    }

    public void fetchUsersFromStore() {
        ArrayList<User> fetchedUsers = storage.deseralizeUser();
        for (User user : fetchedUsers) {
            System.out.println("fetchedUSer:" + user.getUsername() + " " + user.getPassword());
            usersTable.put(user.getUsername(), user);
        }
    }

    /**
     * Handle user authentication.
     */

    public void actionLogin() {
        bLogin.addActionListener(ae -> {
            String strUserName = txtUser.getText();
            String strPass = String.valueOf(pass.getPassword());
            boolean successfulLogin = false;
            boolean credentialsMatch = false;

            String securePassword;
            securePassword = storage.get_SHA_1_SecurePassword(strPass);

            // populate hash table with stored users
            fetchUsersFromStore();
            // iterate over keys
            Iterator<Map.Entry<String, User>> it = usersTable.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, User> entry = it.next();
                if (entry.getKey().equals(strUserName) && entry.getValue().getPassword().equals(securePassword)) {
                    credentialsMatch = true;
                    if (compareTyping(entry.getValue())) {
                        logger.incrementSuccessfulLoginAttempts();
                        logger.writeToLog(strUserName);
                        System.out.println("username: " + strUserName + "logged in");
                        SuccessfulLogin newFrame = new SuccessfulLogin(strUserName);
                        newFrame.setVisible(true);
                        successfulLogin = true;
                    }
                }
            }
            // otherwise its not correct credentials
            if (!successfulLogin) {
                if (credentialsMatch) {
                    JOptionPane.showMessageDialog(null, "Typing cadence does not match!");
                    logger.incrementFailedLoginAttempts();
                    logger.writeToLog(strUserName);
                    txtUser.setText("");
                    pass.setText("");
                    txtUser.requestFocus();
                    // clear array list after failed attempt
                    resetTimings();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong username or password!");
                    logger.incrementFailedLoginAttempts();
                    logger.writeToLog(strUserName);
                    txtUser.setText("");
                    pass.setText("");
                    txtUser.requestFocus();
                    // clear array list after failed attempt
                    resetTimings();
                }
            }
        });
    }

    private void resetTimings() {
        usernameTimings.clear();
        passwordTimings.clear();
    }

    private boolean compareTyping(User user) {
        if (entriesWithinThresholdUsername(user) && entriesWithinThresholdPassword(user)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean entriesWithinThresholdUsername(User user) {
        boolean match = false;
        for (int i = 0; i < usernameTimings.size(); i++) {
            for (int j = 0; j < user.getUsernameTimings().size(); j++) {
                if (Math.abs(usernameTimings.get(i) - user.getUsernameTimings().get(j)) <= threshold) {
                    match = true;
                } else {
                    return false;
                }
            }
        }
        return match;
    }

    private boolean entriesWithinThresholdPassword(User user) {
        boolean match = false;
        for (int i = 0; i < passwordTimings.size(); i++) {
            for (int j = 0; j < user.getPasswordTimings().size(); j++) {
                if (Math.abs(passwordTimings.get(i) - user.getPasswordTimings().get(j)) <= threshold) {
                    match = true;
                } else {
                    return false;
                }
            }
        }
        return match;
    }

    public void registerUser() {
        bRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                // Open a new Frame
                RegisterFrame registerForm = new RegisterFrame("Register", usersTable);
                registerForm.setVisible(true);
                dispose();
                setVisible(true);
            }
        });
    }

    /**
     * Jump through some hoops to avoid
     * trying to print non-printing characters
     * such as Shift.  (Not only do they not print,
     * but if you put them in a String, the characters
     * afterward won't show up in the text area.)
     */

    private void displayInfo(KeyEvent e, String keyStatus) {

        //You should only rely on the key char if the event
        //is a key typed event.
        int id = e.getID();
        String keyString;
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            keyString = "key character = '" + c + "'";
        } else {
            int keyCode = e.getKeyCode();
            keyString = "key code = " + keyCode
                    + " ("
                    + KeyEvent.getKeyText(keyCode)
                    + ")";
        }

        int modifiersEx = e.getModifiersEx();
        String modString = "extended modifiers = " + modifiersEx;
        String tmpString = KeyEvent.getModifiersExText(modifiersEx);
        if (tmpString.length() > 0) {
            modString += " (" + tmpString + ")";
        } else {
            modString += " (no extended modifiers)";
        }

        String actionString = "action key? ";
        if (e.isActionKey()) {
            actionString += "YES";
        } else {
            actionString += "NO";
        }

        String locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
            locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
            locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
            locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
            locationString += "numpad";
        } else {
            // location == KeyEvent.KEY_LOCATION_UNKNOWN
            locationString += "unknown";
        }

        logArea.append(keyStatus + newline
                + "    " + keyString + newline
                + "    " + modString + newline
                + "    " + actionString + newline
                + "    " + locationString + newline);
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }


    /**
     * ===============================================
     *
     *                  Event Handlers
     *
     * ===============================================
     */


    /**
     * Handle the button click.
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        //Clear the text components.
        txtUser.setText("");
        pass.setText("");
        //Return the focus to the typing area.
        txtUser.requestFocusInWindow();
    }


    /**
     * ===============================================
     *
     *  keyPressed - when the key goes down
     *  keyReleased - when the key comes up
     *  keyTyped - when the unicode character represented by this key is sent by the keyboard to system input.
     *
     *  keyPressed is fired whenever any key press occurs.
     *  keyTyped is fired when a key is pressed that can be converted into a unicode character
     *
     * ===============================================
     */

    /**
     * Handle the key typed event from the text field.
     */

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Handle the key pressed event from the text field.
     */

    @Override
    public void keyPressed(KeyEvent e) {
        int textFieldEntered = 0;

        if (e.getSource() == txtUser) {
            System.out.println("Key pressed in txtUser1");
            textFieldEntered = 1;
            recordTimeBetweenKeys(textFieldEntered);
        }
        if (e.getSource() == pass) {
            System.out.println("Key pressed in pass");
            textFieldEntered = 2;
            recordTimeBetweenKeys(textFieldEntered);
        }
    }

    /**
     * Handle the key released event from the text field.
     */

    @Override
    public void keyReleased(KeyEvent e) {
        int textFieldEntered = 0;
        if (e.getSource() == txtUser) {
            System.out.println("Key released in txtUser1");
            textFieldEntered = 1;
        }
        if (e.getSource() == pass) {
            System.out.println("Key released in pass");
            textFieldEntered = 2;
        }
        recordTime(textFieldEntered);
    }

    private void recordTimeBetweenKeys(int textFieldEntered) {
        if (textFieldEntered == 0) {
            System.out.println("recordTimeBetweenKeys textFieldEntered was 0 error");
            System.exit(1);
        }
        if (textFieldEntered == 1) {
            if (usernameTimings.size() == 0) {
                stopWatchUsername.start();
            } else {
                long time = stopWatchUsername.getTime();
                System.out.println("time between key is: " + time);
                usernameTimings.add(time);
                stopWatchUsername.reset();
                stopWatchUsername.start();
            }
        } else if (textFieldEntered == 2) {
            if (passwordTimings.size() == 0) {
                stopWatchPassword.start();
            } else {
                long time = stopWatchPassword.getTime();
                System.out.println("time between key is: " + time);
                passwordTimings.add(time);
                stopWatchPassword.reset();
                stopWatchPassword.start();
            }
        }
    }


    /**
     * Records the time to the users profile.
     */
    private void recordTime(int textFieldEntered) {
        if (textFieldEntered == 0) {
            System.out.println("textFieldEntered was 0 error");
            System.exit(1);
        }
        if (textFieldEntered == 1) {
            stopWatchUsername.stop();
            long time = stopWatchUsername.getTime();
            System.out.println("text 1 key held time is : " + time);
            usernameTimings.add(time);
            stopWatchUsername.reset();
            stopWatchUsername.start();
        } else if (textFieldEntered == 2) {
            stopWatchPassword.stop();
            long time = stopWatchPassword.getTime();
            System.out.println("pass key held time is : " + time);
            passwordTimings.add(time);
            stopWatchPassword.reset();
            stopWatchPassword.start();
        }
    }
}