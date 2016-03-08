import javax.swing.*;


public class NewFrame extends JFrame {

    public static void main(String[] args) {
        NewFrame frameTabel = new NewFrame();
    }

    JLabel welcome = new JLabel("Welcome to a New Frame");
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