import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame implements KeyListener, ActionListener {

    // UI elements
    private static JLabel lblUsername;
    private static JLabel lblPassword;
    private static JButton bLogin;
    private static JButton button;
    private static JTextField txtUser;
    private static JPasswordField pass;
    private static JTextArea logArea;
    private Map<Character, Long> keyPressMap = new HashMap<>();
    private ArrayList<Long> cadenceProfile = new ArrayList<>();
    private static double userCadence;


    static final String newline = System.getProperty("line.separator");

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

        //TODO: Enforce size of UI elements make log big rest small

        // Setup Buttons
        button = new JButton("Clear");
        button.addActionListener(this);
        bLogin = new JButton("Login");
        bLogin.addActionListener(this);

        // Set up labels
        // TODO: set location of these labels
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

        pane.add(lblUsername);
        pane.add(txtUser);
        pane.add(lblPassword);
        pane.add(pass);
        pane.add(scrollPane);
        pane.add(bLogin);
        pane.add(button);
        actionLogin();
    }

    /**
     * Handle user authentication.
     */

    public void actionLogin() {
        bLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String strUserName = txtUser.getText();
                String strPass = pass.getText();
                createCadence();
                if (strUserName.equals("test") && strPass.equals("12345") && userCadence > 0) {
                    NewFrame regFace =new NewFrame();
                    regFace.setVisible(true);
                    dispose();
                } else {

                    JOptionPane.showMessageDialog(null,"Wrong Password / Username");
                    txtUser.setText("");
                    pass.setText("");
                    txtUser.requestFocus();
                }

            }
        });
    }

    /**
     * Create the cadence profile for the user.
     */

    private void createCadence() {
        // TODO: Implement this to check timing of user typing
        // get first character key and time
        for (Map.Entry<Character, Long> entry : keyPressMap.entrySet()) {
            // get next character key and time
            for (Map.Entry<Character, Long> entry2 : keyPressMap.entrySet()) {
                // get first char vals
                Character keyAsChar = entry.getKey();
                long value = entry.getValue();

                // get second char vals
                Character keyAsChar2 = entry2.getKey();
                long value2 = entry2.getValue();

                // calculate the difference between
                long timeDifference = value2 - value;
                cadenceProfile.add(timeDifference);
            }
        }

        for (int i = 0; i < cadenceProfile.size(); i++) {
            //System.out.print(cadenceProfile.get(i) + "\n");
        }

        // create average of profile
        userCadence = calculateAverage(cadenceProfile);
        System.out.print("user cadence: " + userCadence);
    }

    /**
     * Calculates the average time between key presses
     * stored in the cadenceProfile array list.
     */

    private double calculateAverage(ArrayList<Long> cadenceProfile) {
        // TODO: Fix negative values big could be to do with long vs double vs int etc
        // TODO: Get average to function correctly
        double sum = 0;
        if (!cadenceProfile.isEmpty()) {
            System.out.print("cadence profile is not empty \n");
            for (Long difference : cadenceProfile) {
                System.out.print(difference + "\n");
                sum += difference;
            }
            System.out.print("VAL OF SUM: " + sum + "\n");
            double profileResult = sum / cadenceProfile.size();
            return profileResult;
        }
        return sum;
    }

    /**
     * Testing and Debug method used to show
     * the key character pressed and the time
     * at which it was pressed
     */

    private void printCadence() {
        for (Map.Entry<Character, Long> entry : keyPressMap.entrySet()) {
            Character keyAsChar = entry.getKey();
            long value = entry.getValue();
            logArea.append("Key pressed: " + keyAsChar + " At Time: " + value + "\n");
        }
    }

    /**
     *  We have to jump through some hoops to avoid
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
        logArea.setText("");

        //Return the focus to the typing area.
        logArea.requestFocusInWindow();
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
        //displayInfo(e, "KEY TYPED: ");
        keyPressMap.put(e.getKeyChar(), System.currentTimeMillis());
        printCadence();
    }

    /**
     * Handle the key pressed event from the text field.
     */

    @Override
    public void keyPressed(KeyEvent e) {
        //displayInfo(e, "KEY PRESSED: ");
    }

    /**
     * Handle the key released event from the text field.
     */

    @Override
    public void keyReleased(KeyEvent e) {
        //printCadence();
        //displayInfo(e, "KEY RELEASED: ");
    }
}