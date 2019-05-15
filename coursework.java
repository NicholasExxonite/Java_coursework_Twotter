import twooter.Message;
import twooter.TwooterClient;
import twooter.TwooterEvent;
import twooter.UpdateListener;

import java.util.ArrayList;

public class coursework {
    static ArrayList<String> message = new ArrayList<>();

    public static void main(String[] args) {

        TwooterClient client = new TwooterClient();
        UpdateListener upListener = new UpdateListener() {
            @Override
            public void handleUpdate(TwooterEvent twooterEvent) {

            }
        };
        client.addUpdateListener(upListener);
        LogWindow log = new LogWindow(client);
        System.out.println(Signup.cred.getToken("randomness"));

        /*Message msg = new Message("sfs", "name", "hello fucks!", 2000, 2020);
        System.out.println(msg.toString());*/

    }
}
