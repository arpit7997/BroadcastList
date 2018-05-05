package com.joshtalks.arpit.broadcastlist.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joshtalks.arpit.broadcastlist.Interface.ItemClickListner;
import com.joshtalks.arpit.broadcastlist.R;

/**
 * Created by ARPIT on 05-05-2018.
 */

public class BroadCastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtBroadcastNumber;
    private ItemClickListner itemClickListner;

    public BroadCastViewHolder(View itemView) {
        super(itemView);

        txtBroadcastNumber = (TextView) itemView.findViewById(R.id.txtBroadCastNumber);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }


    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(), false);
    }
}
