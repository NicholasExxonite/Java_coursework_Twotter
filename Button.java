import javax.swing.*;

public class Button extends JButton {
    private String butName;
    Button(String name){
        butName = name;
        this.setSize(100, 100 );
        this.setName(butName);
        this.setVisible(true);
    }

    public Button getButton(){
        return this;
    }
}
