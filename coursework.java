import twooter.Message;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class coursework {
    static ArrayList<String> message = new ArrayList<>();
    public static void main(String[] args) {
        JFrame frame = new JFrame("Twotter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 800);


        //creating menubar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Help");
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


        //creating the panel at bottom and adding components
        JPanel panel = new JPanel();

        JLabel label = new JLabel("Enter Text!");
        JTextField tf = new JTextField(10); //accepts up to 10 chars
        JButton send = new JButton("Send");
        JButton reset = new JButton("Reset");
        panel.add(label);
        panel.add(label);
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

        //text area in center
        JTextArea ta = new JTextArea();
        ta.setBackground(Color.LIGHT_GRAY);

        //adding components to the frame
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);


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
}
