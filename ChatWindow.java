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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatWindow extends JFrame{
    //some variables used in the class
    private JMenuBar mb = new JMenuBar();
    private JMenu m1 = new JMenu("File");
    private JMenu m2 = new JMenu("Help");
    private JPanel panel = new JPanel();
    private JTextArea ta = new JTextArea();
    private ArrayList<String> message = new ArrayList<>();
    private JButton send = new JButton("Send");
    private JButton reset = new JButton("Reset");
    private JButton retrieve = new JButton("Retrieve messages");
    private JButton post = new JButton("Post");
    private Message msgToPost = new Message("", "nikolay", "Hello, test", 2000, 2020);
    private Message[] msgs = new Message[30];
    private JScrollPane scrpane = new JScrollPane();
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
                message.add(tf.getText());
                ta.append(message.get(0) + ("\n"));
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
                message.remove(0);
                tf.setText(null);
            }
        });
        // reset the textfield when "reset" is pressed.
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.setText(null);
            }
        });
        post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    client.postMessage(Signup.svTokens.getToken(Signup.username), Signup.username, "howyadoin");
                    System.out.println("Printing message..");
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }
            }
        });
        retrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try{msgs.(client.getMessages().)
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }*/

                /*try{
                    client.postMessage("someToken", "nikolay", "Hello, test!");
                    System.out.println("Posting message!");
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }*/
                try{
                    msgs = client.getMessages();
                    Pattern pattern = Pattern.compile("[name:][.]\b");
                    for (int i = 0; i < msgs.length; i++) {
                        //System.out.println(msgs[i].toString());
                        Message newMsg = msgs[i];
                        /*Matcher matcher = pattern.matcher(newMsg.toString());
                        System.out.println(matcher.group());*/
                        System.out.println(newMsg);
                        ta.append(newMsg + "\n");

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
