package com.ais.mnc.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.VehicleBean;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.ilistener.IItemClickListener;
import com.ais.mnc.view.motorhome.VehicleListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 26/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class VehicleCardAdapter extends RecyclerView.Adapter<VehicleCardViewHolder>{

    Context mContext;
    List<VehicleBean> vehicleList;
    VehicleBean currentVehicleType;

    public VehicleCardAdapter(Context context, List<VehicleBean> vehicleList) {
        mContext = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public VehicleCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vTypeView = LayoutInflater.from(mContext).inflate(R.layout.vehicle_type_layout, viewGroup, false);
        return new VehicleCardViewHolder(vTypeView);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleCardViewHolder vehicleCardViewHolder, final int i) {
        currentVehicleType = vehicleList.get(i);

        //set image and text on card
//        Picasso.with(mContext)
//                .load(currentVehicleType.getImage())
//                .into(vehicleCardViewHolder.vcard_img_item);
        MncUtilities.setMncImage(mContext, currentVehicleType.getImage(), vehicleCardViewHolder.vcard_img_item);
        vehicleCardViewHolder.vcard_tv_desc.setText(currentVehicleType.getVname());

        //set event
        vehicleCardViewHolder.setCardClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                //save the current vehicle type
                MncUtilities.currentVehicleType = vehicleList.get(i).getType();
                //start v list activity
                MncUtilities.startNextActivity(mContext, VehicleListActivity.class, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }
}
