import javax.swing.*;


public class NewFrame extends JFrame {

    JLabel welcome = new JLabel("Congratulations you have accessed id's area");
    JPanel panel = new JPanel();

    NewFrame(){
        super("Successful Login");
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