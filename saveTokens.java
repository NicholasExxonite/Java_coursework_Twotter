import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class saveTokens {
    public static Preferences credentials = Preferences.userRoot().node("tokens.txt");
    private String name;
    private String token;
    private String[] names;

    //method for saving the user credentials, takes name and token as parameters
    public void setCredentials(String n, String t){

        name = n;
        token = t;

        //save the name and token in an external txt file.
        credentials.put(name, token);
        try {
            credentials.exportNode(new FileOutputStream("tokens.txt"));
        }catch (IOException | BackingStoreException e1){
            System.out.println(e1);
        }
    }
    //method returning the token of the username provided as a parameter.
    public String getToken(String name){

        return credentials.get(name, token);
    }

    //method to get all the registered usernames in an array.
    public String[] getUsers(){
        try {
            names = credentials.keys();
        }catch (java.util.prefs.BackingStoreException e1){
            System.out.println(e1);
        }
        return names;
    }
}
