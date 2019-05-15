import twooter.TwooterClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Signup extends JFrame {
    private JTextField name = new JTextField(10 );
    private JLabel lb = new JLabel();
    private JLabel luser = new JLabel();
    private JButton register = new JButton("Register");
    private JPanel panel = new JPanel();
    public static String token;
    public static String username;
    //private String regName;
    Signup(String name, TwooterClient client){
        this.setName(name);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setUp();
        accRegister(client);
        this.setVisible(true);

    }
    private void setUp(){
        luser.setText("Username:");
        panel.add(luser);
        panel.add(name);
        panel.add(register);
        this.getContentPane().add(BorderLayout.SOUTH, panel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        lb.setText("Please choose the username with which you want to be known");
        this.getContentPane().add(BorderLayout.NORTH, lb);
    }
    public void accRegister(TwooterClient client){

        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*regName = name.getText();*/
                try {
                    username = name.getText();
                    token = client.registerName(username);
                    System.out.println("Successfully registered!");
                    //token = client.registerName(name.getText());
                    System.out.println("your token is: " + token);
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }
            }
        });
    }
}
