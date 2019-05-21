import twooter.Message;
import twooter.TwooterClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatWindow extends JFrame{
    //some variables used in the class
    private JMenuBar mb = new JMenuBar();
    private JMenu m1 = new JMenu("File");
    private JMenu m2 = new JMenu("Help");
    private JPanel panel = new JPanel();
    private JTextArea ta = new JTextArea();
    //private ArrayList<String> message = new ArrayList<>();
    private JButton send = new JButton("Send");
    private JButton reset = new JButton("Reset");
    private JButton retrieve = new JButton("Retrieve messages");
    private JButton post = new JButton("Post");
    //private Message msgToPost = new Message("", "nikolay", "Hello, test", 2000, 2020);
    private Message[] msgs = new Message[30];
    //a boolean, so we can separately post to both our application and the twooter client
    private Boolean toweb = true;

    private Pattern pname = Pattern.compile("name:[a-zA-Z0-9\\s'#\"!@$%^&*()-]*");
    private Pattern pmessage = Pattern.compile("message:[a-zA-Z0-9\\s':;/.\\\\?><|`~{}\\]\\[\"#!@$%^&*()-]*");

    //constructor for the ChatWindow, takes the client as a parameter.
    ChatWindow(TwooterClient client){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(600, 800);
        //enabling the live feed.
        client.enableLiveFeed();

        setMenuBar();
        setPanel(client);
        setTextArea();
        //add textArea to a scroll pane so it can be scrollable.
        /*scrpane.createVerticalScrollBar();
        scrpane.add(ta);
        scrpane.setLayout(new ScrollPaneLayout());*/
        //adding components to the frame
        this.getContentPane().add(BorderLayout.SOUTH, panel);
        this.getContentPane().add(BorderLayout.NORTH, mb);
        this.getContentPane().add(BorderLayout.CENTER, ta);
        //set it visible
        this.setVisible(true);
    }


    public void setMenuBar(){
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Exit");
        //When "Exit" is clicked > close the applications.
        m11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Menu item called "About" for menu "Help", displaying who created the UI
        JLabel author = new JLabel("Created by Nikolay Pankov");
        JMenuItem m21 = new JMenuItem("About");
        m21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame about = new JFrame("About");
                about.setSize(300, 300);
                about.add(author);
                about.setVisible(true);
                about.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
        JMenuItem m12 = new JMenuItem("Save as ");
        m1.add(m11);
        m1.add(m12);
        m2.add(m21);
    }

    //setting up the panel, adding features to the components and adding them to the panel
    public void setPanel(TwooterClient client){
        JLabel label = new JLabel("Enter Text!");
        JTextField tf = new JTextField(10); //accepts up to 10 chars
        panel.add(label);
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        panel.add(reset);
        panel.add(retrieve);
        panel.add(post);

        //Action listeners for sending messages from the textfield to the textarea and for resetting the textArea.
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tf.getText().startsWith("!")){
                    String name = tf.getText().substring(1);
                    try{
                        msgs = client.getMessages(name);
                        for (int i=0; i < msgs.length; i++){
                            Message msg = msgs[i];
                            Matcher mmessage = pmessage.matcher(msg.toString());
                            if(mmessage.find()){
                                ta.append(name + ": " + mmessage.group().substring(8) + "\n");
                            }
                        }
                    }catch (java.io.IOException e1){
                        System.out.println(e1);
                    }
                }

                ta.append(Signup.username + ": " + tf.getText() + "\n");
                //Just some tests for me!
                try{
                    System.out.println("Username " + tf.getText() + " is active:" + client.isActiveName(tf.getText()));
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }
                try{
                    System.out.println("The feed is up: " + client.isFeedConnected() + " and the web service is online and reachable:"
                            + client.isUp());
                }catch (java.io.IOException e2){
                    System.out.println(e2);
                }
                // end of tests
                tf.setText(null);
            }
        });
        // reset the textarea when "reset" is pressed.
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.setText(null);
            }
        });
        //post a message to the client
        post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    client.postMessage(Signup.svTokens.getToken(Signup.username), Signup.username, tf.getText());
                    ta.append(Signup.username + ": " + tf.getText() + "\n");
                    System.out.println("Printing message..");
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }
                tf.setText(null);
            }
        });
        retrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    msgs = client.getMessages();
                    //String regex = "name:[a-zA-Z_0-9]*";
//                    Pattern pname = Pattern.compile("name:[a-zA-Z0-9\\s'#\"!@$%^&*()-]*");
//                    Pattern pmessage = Pattern.compile("message:[a-zA-Z0-9\\s'#!@$%\"^&*()-]*");
//                    Pattern pmessage = Pattern.compile("message:[a-zA-Z0-9\\s':;/.\\\\?><|`~{}\\]\\[\"#!@$%^&*()-]*");
                    for (int i = 0; i < msgs.length; i++) {
                        //System.out.println(msgs[i].toString());
                        Message newMsg = msgs[i];
                        Matcher mname = pname.matcher(newMsg.toString());
                        Matcher mmessage = pmessage.matcher(newMsg.toString());
                        //boolean matches = Pattern.matches(regex, newMsg.toString());
                        //System.out.println(matches);
                        if(mname.find() && mmessage.find()){
                            String name = mname.group().substring(5);
                            String message = mmessage.group().substring(8);
                            //System.out.println(matcher.group());
                            System.out.println(name + ": " + message);
                            ta.append(name + ": " + message +"\n");

                        }

                    }
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }
            }
        });

    }
    //some cosmetics
    public void setTextArea(){
        //text area in center
        /*ta.setLineWrap(true);
        ta.setWrapStyleWord(true);*/
        ta.setBackground(Color.LIGHT_GRAY);
    }
}
