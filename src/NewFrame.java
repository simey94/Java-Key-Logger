import javax.swing.*;


public class NewFrame extends JFrame {

    JLabel welcome = new JLabel("Congratulations you have accessed ID’s area");
    JPanel panel = new JPanel();

    NewFrame(){
        super("Welcome");
        setSize(300,200);
        setLocation(500,280);
        panel.setLayout (null);

        welcome.setBounds(70,50,200,60);

        panel.add(welcome);

        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}