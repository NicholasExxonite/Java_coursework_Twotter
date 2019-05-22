import twooter.Message;
import twooter.TwooterClient;
import twooter.TwooterEvent;
import twooter.UpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private JButton send = new JButton("Send");
    private JButton reset = new JButton("Reset");
    private JButton retrieve = new JButton("Retrieve messages");
    private JButton post = new JButton("Post");
    //array of messages to contain the messages from the getMessages method. Max 30 messages.
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
        //adding components to the frame
        this.getContentPane().add(BorderLayout.SOUTH, panel);
        this.getContentPane().add(BorderLayout.NORTH, mb);
        this.getContentPane().add(BorderLayout.CENTER, ta);
        //set it visible
        this.setVisible(true);
        //Updatelistener for the events
        UpdateListener upListener = new UpdateListener() {
            @Override
            public void handleUpdate(TwooterEvent twooterEvent) {
                //if the even is a message(2) then print it using the retrieveMsgs method!

                if(twooterEvent.type == 2 ){
                    retrieveMsgs(twooterEvent.payload, client);
                }
            }
        };
        //add the updatelistener to the client.
        client.addUpdateListener(upListener);
    }

    //Method setting the menubar of the application.
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
                about.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });
        //when clicked save the current chat into a textfile.
        JMenuItem m12 = new JMenuItem("Save");
        m12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    //Save the contents of the textarea into the txt file
                    BufferedWriter fileOut = new BufferedWriter(new FileWriter("savedChat.txt"));
                    ta.write(fileOut);
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        });
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
                //this following code returns the last 30 messages from a specific username. You have to type ! and then
                // the username of the user: !someusername
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

                //this following code just tests the web service, the feed and if the name typed in the textfield is an active user.
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
                    //we don't need to post the message to our application as well, since the application automatically will get what we
                    //posted on the web client and post it in the application.
                    //ta.append(Signup.username + ": " + tf.getText() + "\n");
                    System.out.println("Printing message..");
                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }
                tf.setText(null);
            }
        });

        //retrieve the last 30 messages from the web client
        retrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    msgs = client.getMessages();
                    retrieveMsgs(msgs.length);

                }catch (java.io.IOException e1){
                    System.out.println(e1);
                }
            }
        });

    }

    //method for retrieving the messages from the client. Takes as a parameter an int (num) , which specifies how many
    //messages to be retrieved.
    public void retrieveMsgs(int num) {
        for (int i = 0; i < num; i++) {
            //System.out.println(msgs[i].toString());
            Message newMsg = msgs[i];
            Matcher mname = pname.matcher(newMsg.toString());
            Matcher mmessage = pmessage.matcher(newMsg.toString());
            //boolean matches = Pattern.matches(regex, newMsg.toString());
            //System.out.println(matches);
            if (mname.find() && mmessage.find()) {
                String name = mname.group().substring(5);
                String message = mmessage.group().substring(8);
                //System.out.println(matcher.group());
                System.out.println(name + ": " + message);
                ta.append(name + ": " + message + "\n");
            }
        }
    }

    //another method for retrieving messages , but this one takes the payload from the updatelistener and the client parameters
    //it returns the each message posted to the web client automatically , live.
    public void retrieveMsgs(String payload, TwooterClient client) {

        Message newMsg = null;
        try{
             newMsg = client.getMessage(payload);
        }catch (java.io.IOException e1){
            System.out.println(e1);
        }
        Matcher mname = pname.matcher(newMsg.toString());
        Matcher mmessage = pmessage.matcher(newMsg.toString());
        //boolean matches = Pattern.matches(regex, newMsg.toString());
        //System.out.println(matches);
        if (mname.find() && mmessage.find()) {
            String name = mname.group().substring(5);
            String message = mmessage.group().substring(8);
            //System.out.println(matcher.group());
            System.out.println(name + ": " + message);
            ta.append(name + ": " + message + "\n");
            }
        }
    //some cosmetics
    public void setTextArea() {
        //text area in center
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBackground(Color.LIGHT_GRAY);
    }
}

