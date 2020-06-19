import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class Main {

private static String adminSDKAddress=null;
//Enter it, example- ./clubevents-49b45-firebase-adminsdk-fly3f-8356aeb6cd.json
private static String DatabaseURL=null;
//Enter it, example- https://clubevents-49b45.firebaseio.com

    public static void main(String[] args) throws Exception {

        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");

        FileInputStream serviceAccount =
                new FileInputStream(adminSDKAddress);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(DatabaseURL)
                .build();

        FirebaseApp.initializeApp(options);


        ClubsManager clubsManager=new ClubsManager();




    }
}
