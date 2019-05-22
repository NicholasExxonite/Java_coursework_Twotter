import twooter.TwooterClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    //some variables

    private JTextField username = new JTextField(10);
    private JPanel north = new JPanel();
    private JPanel south = new JPanel();
    private JLabel usr = new JLabel();
    private JButton b = new JButton("Log in");
    private JLabel alert = new JLabel();
    JLabel test = new JLabel();
    //constructor, takes the client as a parameter.
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
    //setting the UI
    private void setCredentials(){
        usr.setText("Username:");
        username.setName("Username");
        north.setLayout(new FlowLayout());
        north.add(usr);
        north.add(username);
        north.add(b);

        south.setLayout(new FlowLayout());
        /*south.add(pas);
        south.add(password);*/
        south.add(test);
        south.add(alert);
    }
    //log in button
    private void checkLogin(TwooterClient client){
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //if this user is active(aka exists), then log me in
                    boolean bool = client.isActiveName(username.getText());
                    if (bool){
                        Signup.username = username.getText();
                        test.setText("You've logged in!");
                        System.out.println("You're in!");
                        ChatWindow chatWindow = new ChatWindow(client);
                    }
                    else{
                        alert.setText("User with such name doesn't exist.");
                        System.out.println("This user doesn't exist!");
                        saveTokens.credentials.remove(username.getText());
                        Signup sg = new Signup("Sign up!", client);
                    }
                }catch (java.io.IOException | java.lang.NullPointerException e1){
                    System.out.println(e1);
                }
            }
        });
    }

}
