import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChatWindow extends JFrame{
    private JMenuBar mb = new JMenuBar();
    private JMenu m1 = new JMenu("File");
    private JMenu m2 = new JMenu("Help");
    private JPanel panel = new JPanel();
    private JTextArea ta = new JTextArea();
    private ArrayList<String> message = new ArrayList<>();

    ChatWindow(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(600, 800);

        setMenuBar();
        setPanel();
        setTextArea();
        //adding components to the frame
        this.getContentPane().add(BorderLayout.SOUTH, panel);
        this.getContentPane().add(BorderLayout.NORTH, mb);
        this.getContentPane().add(BorderLayout.CENTER, ta);
        this.setVisible(true);
    }


    public void setMenuBar(){
        //creating menubar and adding components
        //JMenuBar mb = new JMenuBar();
        /*JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Help");*/

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

    public void setPanel(){
        //creating the panel at bottom and adding components

        JLabel label = new JLabel("Enter Text!");
        JTextField tf = new JTextField(10); //accepts up to 10 chars
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label);
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        //Action listeners for sending messages from the textfield to the textarea and for resetting the textArea.
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.add(tf.getText());
                ta.append(message.get(0) + ("\n"));
                message.remove(0);
                tf.setText(null);
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ta.setText(null);
            }
        });

    }
    public void setTextArea(){
        //text area in center
        ta.setBackground(Color.LIGHT_GRAY);
    }

}
