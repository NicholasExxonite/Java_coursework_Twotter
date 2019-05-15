import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class saveTokens {
    public static Preferences credentials = Preferences.userRoot().node("tokens.txt");
    private String name;
    private String token;

    public void setCredentials(String n, String t){

        name = n;
        token = t;
        credentials.put(name, token);
        try {
            credentials.exportNode(new FileOutputStream("tokens.txt"));
        }catch (IOException | BackingStoreException e1){
            System.out.println(e1);
        }
    }
    public String getToken(String name){
        return credentials.get(name, token);
    }
}
