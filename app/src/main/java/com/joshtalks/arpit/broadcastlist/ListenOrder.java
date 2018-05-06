package com.joshtalks.arpit.broadcastlist;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by ARPIT on 05-05-2018.
 */

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase database;
    DatabaseReference broadcast;
    public ListenOrder() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = FirebaseDatabase.getInstance();
        broadcast = database.getReference("BroadCast");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        broadcast.addChildEventListener(this);
        Log.d("HELLOOO","ListenOredrClass");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // if we add any child in the database, this will get called and a notification will be send to the user.
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        //OnClick notification opens this activity.
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(ListenOrder.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());

        //notification content
        builder.setAutoCancel(true)
                .setSmallIcon(R.drawable.logo)
                .setContentInfo("New Broadcast")
                .setContentText("You have new Broadcast from Josh Talks")
                .setTicker("Josh Talks").setContentTitle("Josh Talks")
                .setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_ALL);

        NotificationManager manager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
