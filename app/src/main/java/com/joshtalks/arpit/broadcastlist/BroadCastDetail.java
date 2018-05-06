package com.joshtalks.arpit.broadcastlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joshtalks.arpit.broadcastlist.Model.BroadCast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BroadCastDetail extends AppCompatActivity {

    TextView timePast, bradcastNum, message, link, authorName, timeToRead,text;
    Button showAllBradCast;

    String broadCastId = "";

    BroadCast currentBroadCast;

    FirebaseDatabase database;
    DatabaseReference broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast_detail);

        database = FirebaseDatabase.getInstance();
        broadcast = database.getReference("BroadCast");


        timePast = (TextView) findViewById(R.id.time);
        bradcastNum = (TextView) findViewById(R.id.text_broadcastNum);
        text = (TextView) findViewById(R.id.text);
        message = (TextView) findViewById(R.id.text_broadcastMessage);
        link = (TextView) findViewById(R.id.text_bradcastLink);
        link.setClickable(true);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        authorName = (TextView) findViewById(R.id.text_bradcastAuthorName);
        timeToRead = (TextView) findViewById(R.id.text_bradcastTimeToRead);

        showAllBradCast = (Button) findViewById(R.id.btn_showListBroadcast);

        //checking if the id passed id null or not
        if (getIntent().getExtras() != null) {
            broadCastId = getIntent().getStringExtra("BroadCastId");

            //if not null, show the  boradcast clicked by user
            if (broadCastId != null) {
                loadBroadCast();
            }
        }
        //if null, show todays broadcast
        else{
            broadcast.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot postSnap: dataSnapshot.getChildren()) {

                        currentBroadCast = postSnap.getValue(BroadCast.class);
                        if (currentBroadCast != null) {
                            String startDateValue = currentBroadCast.getDate();
                            Date todayDateValue = new Date();
                            Date date = null;
                            //converting string into date format
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                date = sdf.parse(startDateValue);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            long diff = todayDateValue.getTime() - date.getTime();
                            long seconds = diff / 1000;
                            long minutes = seconds / 60;
                            long hours = minutes / 60;
                            long days = (hours / 24);

                            //if the diff between days is 0, means its todays broadcast
                            if (days == 0) {
                                broadCastId = postSnap.getKey();
                                loadBroadCast();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        showAllBradCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BroadCastDetail.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadBroadCast() {

        //showing broadcast details of the key
        broadcast.child(broadCastId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                currentBroadCast = dataSnapshot.getValue(BroadCast.class);

                if(currentBroadCast!=null) {

                    message.setText(currentBroadCast.getMessage());
                    authorName.setText("- " + currentBroadCast.getAuthorName());
                    timeToRead.setText("(" + currentBroadCast.getTimeToRead() + " mins read)");

                    //setting up link
                    link.setText(Html.fromHtml("<a href ='"+currentBroadCast.getLink()+"'>Link to full Broadcast<a/>"));
                    bradcastNum.setText("#" + currentBroadCast.getBroadCastNum()+" - Read This Today");
                    String startDateValue = currentBroadCast.getDate();
                    Date todayDateValue = new Date();
                    Date date = null;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        date = sdf.parse(startDateValue);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long diff = todayDateValue.getTime() - date.getTime();
                    long seconds = diff / 1000;
                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    long days = (hours / 24);

                    //checking if it's todays broadcast or old
                    //if todays, show the hours
                    if(days == 0){
                        text.setText("Today's Broadcast");
                        timePast.setText(hours+"hr");
                    }else{
                        //if past, show the days difference
                        timePast.setText(days+"d");
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
