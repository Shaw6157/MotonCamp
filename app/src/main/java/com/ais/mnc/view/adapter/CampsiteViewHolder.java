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
 * Created on 24/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class CampsiteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView ccard_img_campsite;
    public TextView  ccard_tv_desc;

    IItemClickListener mItemClickListener;

    public void setItemClickListener(IItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public CampsiteViewHolder(@NonNull View camplistView) {
        super(camplistView);

        ccard_img_campsite = camplistView.findViewById(R.id.ccard_img_campsite);
        ccard_tv_desc = camplistView.findViewById(R.id.ccard_tv_desc);

        camplistView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onClick(v);
    }
}
