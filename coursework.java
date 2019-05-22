import twooter.Message;
import twooter.TwooterClient;
import twooter.TwooterEvent;
import twooter.UpdateListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class coursework {
    static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    //static ArrayList<String> message = new ArrayList<>();

    public static void main(String[] args) {
        //initialize the twooter client
        TwooterClient client = new TwooterClient();

        //create a runnable containing the refreshName method of the twooter client.
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //get the username and token directly from the txt file that they
                // are saved in and refresh them with the scheduler bellow.
                try{
                    for(String s : saveTokens.credentials.keys()){
                        client.refreshName(s, Signup.svTokens.getToken(s));
                    }
                }catch (IOException | BackingStoreException e1){
                    System.out.println(e1);
                }
            }
        };
        //A scheduler using the previously created runnable variable. Periodically refreshes the user.
        scheduler.scheduleAtFixedRate(runnable, 8, 8, TimeUnit.HOURS);

        //initialize the log in window
        LogWindow log = new LogWindow(client);

    }
}