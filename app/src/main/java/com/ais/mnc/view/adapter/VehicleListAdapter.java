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
import com.ais.mnc.view.motorhome.VehicleDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 27/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListViewHolder> {
    Context mContext;
    List<VehicleBean> vehicleList;
    VehicleBean currentVehicle;

    public VehicleListAdapter(Context context, List<VehicleBean> vehicleList) {
        mContext = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public VehicleListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vTypeView = LayoutInflater.from(mContext).inflate(R.layout.vehicle_list_layout, viewGroup, false);
        return new VehicleListViewHolder(vTypeView);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleListViewHolder holder, final int i) {
        currentVehicle = vehicleList.get(i);

//        Picasso.with(mContext)
//                .load(currentVehicle.getImage())
//                .into(holder.vlst_img);
        MncUtilities.setMncImage(mContext, currentVehicle.getImage(), holder.vlst_img);
        holder.vlst_tv_name.setText(currentVehicle.getVname());
        holder.vlst_tv_type.setText(currentVehicle.getType() + " Berth");
        holder.vlst_tv_transm.setText(currentVehicle.getTransmission());
        holder.vlst_tv_engine.setText(currentVehicle.getEngin());
        holder.vlst_tv_price.setText(" $" + currentVehicle.getPrice());

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
//                MncUtilities.toastMessage(mContext, "vlist item clicked... ");
                //set to global
                MncUtilities.currentVehicle = vehicleList.get(i);
                //start activity to details
                MncUtilities.startNextActivity(mContext, VehicleDetailActivity.class, "" + vehicleList.get(i).getVid(), false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }
}
