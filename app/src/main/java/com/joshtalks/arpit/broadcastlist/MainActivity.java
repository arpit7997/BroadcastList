package com.joshtalks.arpit.broadcastlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.joshtalks.arpit.broadcastlist.Interface.ItemClickListner;
import com.joshtalks.arpit.broadcastlist.Model.BroadCast;
import com.joshtalks.arpit.broadcastlist.ViewHolder.BroadCastViewHolder;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference broadcast;


    RecyclerView recycler_broadcast;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<BroadCast, BroadCastViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        database = FirebaseDatabase.getInstance();
        broadcast = database.getReference("BroadCast");

        recycler_broadcast = (RecyclerView) findViewById(R.id.recyclerView_broadcast);

        recycler_broadcast.setNestedScrollingEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        recycler_broadcast.setLayoutManager(layoutManager);

        //animating recycler view
        int resId = R.anim.layout_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getBaseContext(), resId);
        recycler_broadcast.setLayoutAnimation(animation);

        //calling function to load all the broadcast
        loadBroadCast();

        runLayoutAnimation(recycler_broadcast);

    }

    private void loadBroadCast() {


        //setting up the recyclerView
        adapter = new FirebaseRecyclerAdapter<BroadCast, BroadCastViewHolder>(BroadCast.class,
                R.layout.broadcast_item,
                BroadCastViewHolder.class,
                broadcast.orderByChild("Date")) {

            @Override
            protected void populateViewHolder(BroadCastViewHolder viewHolder, BroadCast model, int position) {

                //setting name of broadcast
                viewHolder.txtBroadcastNumber.setText("Broadcast #" + model.getBroadCastNum());


                viewHolder.setItemClickListner(new ItemClickListner() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //sending the key of the item clicked
                        Intent billDetail = new Intent(MainActivity.this, BroadCastDetail.class);
                        billDetail.putExtra("BroadCastId", adapter.getRef(position).getKey());
                        startActivity(billDetail);
                    }
                });
            }

        };
        adapter.notifyDataSetChanged();
        recycler_broadcast.setAdapter(adapter);
        runLayoutAnimation(recycler_broadcast);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {


        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}
