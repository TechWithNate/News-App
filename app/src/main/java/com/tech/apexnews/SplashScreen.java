package com.tech.apexnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {


    private static final int SPLASH_TIMER = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        // Splash screen delay timer
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Navigate to Home activity
                startActivity(new Intent(SplashScreen.this, Home.class));
                finish();
            }
        }, SPLASH_TIMER);

    }
}