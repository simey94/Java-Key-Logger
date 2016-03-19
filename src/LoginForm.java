import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class LoginForm extends JFrame implements KeyListener, ActionListener {

    // UI elements
    private static JButton bLogin;
    private static JButton button;
    private static JTextField txtUser;
    private static JPasswordField pass;
    private static JTextArea logArea;
    private Map<Integer, Long> keyPressMap = new HashMap<>();


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

        // Set up text fields
        txtUser = new JTextField(15);
        pass = new JPasswordField(15);
        txtUser.addKeyListener(this);
        pass.addKeyListener(this);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(375, 125));

        txtUser.setAlignmentX(pane.CENTER_ALIGNMENT);
        pass.setAlignmentX(pane.CENTER_ALIGNMENT);
        scrollPane.setAlignmentX(pane.CENTER_ALIGNMENT);
        bLogin.setAlignmentX(pane.CENTER_ALIGNMENT);
        button.setAlignmentX(pane.CENTER_ALIGNMENT);

        //getContentPane().add(scrollPane, BorderLayout.CENTER);

        pane.add(txtUser);
        pane.add(pass);
        pane.add(scrollPane);
        pane.add(bLogin);
        pane.add(button);
        actionlogin();
    }

    /**
     * Handle user authentication.
     */

    public void actionlogin(){
        bLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String strUserName = txtUser.getText();
                String strPass = pass.getText();
                if(strUserName.equals("test") && strPass.equals("12345")) {
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
     * Check the rhythm and cadence of the user login.
     */

    private boolean checkCadence() {
        // TODO: Implement this to check timing of user typing
        // find out if key is pressed and how long it was
        Long t = keyPressMap.get(KeyEvent.VK_SPACE);
        if (t == null) {
            // not pressed
            return false;
        } else {
            // pressed for X milliseconds
            long millis = t - System.currentTimeMillis();
        }
        return true;
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
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
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
     * Handle the key typed event from the text field.
     */

    @Override
    public void keyTyped(KeyEvent e) {
        displayInfo(e, "KEY TYPED: ");
    }

    /**
     * Handle the key pressed event from the text field.
     */

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressMap.put(e.getKeyCode(), System.currentTimeMillis());
        displayInfo(e, "KEY PRESSED: ");
    }

    /**
     * Handle the key released event from the text field.
     */

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressMap.remove(e.getKeyCode());
        displayInfo(e, "KEY RELEASED: ");
    }
}