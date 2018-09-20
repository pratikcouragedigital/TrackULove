package com.mobitechs.pratik.trackuloved;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import com.mobitechs.pratik.trackuloved.sessionManager.SessionManager;


public class Splash_Screen extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    ImageView image1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

         //image1 = (ImageView)findViewById(R.id.logoHeader);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SessionManager sessionManager = new SessionManager(Splash_Screen.this);
                sessionManager.checkLogin();

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}