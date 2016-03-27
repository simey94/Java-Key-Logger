import javax.swing.*;


public class SuccessfulLogin extends JFrame {

    JLabel welcome;
    JPanel panel;

    SuccessfulLogin(String strUserName) {
        super("Successful Login");
        panel = new JPanel();
        welcome = new JLabel("Congratulations you have accessed " + strUserName + "'s area!");
        setSize(600, 300);
        setLocation(500,280);
        panel.setLayout (null);

        welcome.setBounds(70, 50, 400, 60);

        panel.add(welcome);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}