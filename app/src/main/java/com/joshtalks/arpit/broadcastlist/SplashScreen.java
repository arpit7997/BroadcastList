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
            //checking if  the app is launched for first time
            //if yes, open the slider activity
            if(firstTimeLauch == null){

                Intent i = new Intent(SplashScreen.this, SwipingScreen.class);
                startActivity(i);
                //save in localdb
                Paper.book().write("firstTime","1");
            }
            else{
                //not launched for first time.
                Intent i = new Intent(SplashScreen.this, BroadCastDetail.class);
                startActivity(i);

                //starting the service of notification.
                Intent service = new Intent(SplashScreen.this, ListenOrder.class);
                startService(service);

            }


            finish();
        }
    }, SPLASH_TIME_OUT);
}
}
