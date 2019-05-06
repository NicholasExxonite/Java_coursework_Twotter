import twooter.TwooterClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private String usrname;
    private JTextField username = new JTextField(10);
    private JTextField password = new JTextField(10);
    private JPanel north = new JPanel();
    private JPanel south = new JPanel();
    private JLabel usr = new JLabel();
    private JLabel pas = new JLabel();
    private JButton b = new JButton("Log in");
    JLabel test = new JLabel();
    public Login(TwooterClient client){
        this.setName("Log in");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setCredentials();
        this.getContentPane().add(BorderLayout.NORTH, north);
        this.getContentPane().add(BorderLayout.SOUTH, south);
        checkLogin(client);
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
        north.add(b);

        south.setLayout(new FlowLayout());
        /*south.add(pas);
        south.add(password);*/
        south.add(test);
    }
    private void checkLogin(TwooterClient client){
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    boolean bool = client.isActiveName(username.getText());
                    if (bool){
                        test.setText("You've logged in!");
                        System.out.println("You're in!");
                        ChatWindow chatWindow = new ChatWindow(client);
                    }
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }
            }
        });
    }

}
