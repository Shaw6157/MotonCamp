package com.ais.mnc.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.ilistener.IItemClickListener;
import com.ais.mnc.view.campsite.CsDetailActivity;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 24/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class CampsiteListAdapter extends RecyclerView.Adapter<CampsiteViewHolder> {
    Context mContext;
    List<CampBean> campsiteList;

    public CampsiteListAdapter(Context pContext, List<CampBean> pCampsiteList) {
        mContext = pContext;
        campsiteList = pCampsiteList;
    }

    @NonNull
    @Override
    public CampsiteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View campsiteView = LayoutInflater.from(mContext).inflate(R.layout.campsite_card_layout, viewGroup, false);
        return new CampsiteViewHolder(campsiteView);
    }

    @Override
    public void onBindViewHolder(@NonNull CampsiteViewHolder cpHolder, final int position) {
        CampBean currentCamp = campsiteList.get(position);
        MncUtilities.loadOnlineImage(mContext, currentCamp.getImage(), cpHolder.ccard_img_campsite);
        cpHolder.ccard_tv_desc.setText(campsiteList.get(position).getCname());

        cpHolder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                //set to global cpbean
                MncUtilities.currentCpsite = campsiteList.get(position);
                //start activity to details
                MncUtilities.startNextActivity(mContext, CsDetailActivity.class, false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return campsiteList.size();
    }
}
