import twooter.TwooterClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogWindow extends JFrame {
    private JPanel buttons = new JPanel();
    private JButton login = new JButton("Log in");
    private JButton signup = new JButton("Sign up");
    private JLabel message = new JLabel();
    private TwooterClient cl;

    //constructor for he login window.
    public LogWindow(TwooterClient client)
    {
        cl = client;
        this.setName("Welcome to Twotter!");
        this.setSize(600,600);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        buttons.setLayout(new FlowLayout());
        setButtons();
        setIntroMessage();
        //this.add(buttons);
        this.getContentPane().add(BorderLayout.NORTH, message);
        this.getContentPane().add(BorderLayout.SOUTH, buttons);
        this.pack();

        this.setVisible(true);
    }

    private void setButtons(){
        login.setSize(200, 200);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Login login = new Login(cl);
            }
        });
        signup.setSize(200, 200);
        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signup sigup = new Signup("Sign up!", cl);
            }
        });
        buttons.add(login);
        buttons.add(signup);
    }
    private void setIntroMessage(){
        message.setText("Welcome to the Twotter client! If you have an account please click log in, otherwise click on sign in to create an account!");
    }

}
