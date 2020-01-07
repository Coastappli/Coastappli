package osirisc.coastappli;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This splashscreen is launched when the app is opened in order to hide the loading time
 * It shows the app's icon
 */
public class SplashScreen extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //We wait one second for the app to load in background
        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);  //Delay of 1 seconds
                } catch (Exception e) {
                    //There should be no errors
                } finally {

                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
