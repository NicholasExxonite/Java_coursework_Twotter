package twooter;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField username = new JTextField(10);
    private JTextField password = new JTextField(10);
    private JPanel north = new JPanel();
    private JPanel south = new JPanel();
    private JLabel usr = new JLabel();
    private JLabel pas = new JLabel();
    public Login(){
        this.setName("Log in");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setCredentials();
        this.getContentPane().add(BorderLayout.NORTH, north);
        this.getContentPane().add(BorderLayout.SOUTH, south);

        this.setVisible(true);
    }
    private void setCredentials(){
        usr.setText("Username:");
        pas.setText("Password:");
        username.setName("Username");
        password.setName("Password");
        north.setLayout(new FlowLayout());
        north.add(usr);
        north.add(username);

        south.setLayout(new FlowLayout());
        south.add(pas);
        south.add(password);
    }

}
