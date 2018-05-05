package com.joshtalks.arpit.broadcastlist;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference broadcast;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        database = FirebaseDatabase.getInstance();
        broadcast = database.getReference("BroadCast");
        Paper.init(this);

        final String firstTimeLauch = Paper.book().read("firstTime");

    new Handler().postDelayed(new Runnable() {

        @Override
        public void run() {
            if(firstTimeLauch == null){

                Intent i = new Intent(SplashScreen.this, SwipingScreen.class);
                startActivity(i);
                Paper.book().write("firstTime","1");
            }
            else{
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
            }


            finish();
        }
    }, SPLASH_TIME_OUT);
}
}
