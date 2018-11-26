package com.ais.mnc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.view.adapter.ilistener.IItemClickListener;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 27/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class VehicleListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView vlst_img;
    TextView vlst_tv_name, vlst_tv_type, vlst_tv_transm, vlst_tv_engine, vlst_tv_price;

    IItemClickListener mItemClickListener;

    public void setItemClickListener(IItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public VehicleListViewHolder(@NonNull View itemView) {
        super(itemView);

        vlst_img = itemView.findViewById(R.id.vlst_img);
        vlst_tv_name = itemView.findViewById(R.id.vlst_tv_name);
        vlst_tv_type = itemView.findViewById(R.id.vlst_tv_type);
        vlst_tv_transm = itemView.findViewById(R.id.vlst_tv_transmission);
        vlst_tv_engine = itemView.findViewById(R.id.vlst_tv_engine);
        vlst_tv_price = itemView.findViewById(R.id.vlst_tv_amount);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onClick(v);
    }
}
