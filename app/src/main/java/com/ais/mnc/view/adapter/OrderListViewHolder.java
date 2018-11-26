package com.ais.mnc.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.view.adapter.ilistener.IItemClickListener;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 29/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class OrderListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView odlt_tv_vname,
            odlt_tv_oid,
            odlt_tv_amount,
            odlt_tv_datebg,
            odlt_tv_dateed,
            odlt_tv_status;

    IItemClickListener mListener;

    public void setListener(IItemClickListener listener) {
        mListener = listener;
    }

    public OrderListViewHolder(@NonNull View itemView, IItemClickListener listener) {
        super(itemView);

        mListener = listener;

        odlt_tv_vname = itemView.findViewById(R.id.odlt_tv_vname);
        odlt_tv_oid = itemView.findViewById(R.id.odlt_tv_oid);
        odlt_tv_amount = itemView.findViewById(R.id.odlt_tv_amount);
        odlt_tv_datebg = itemView.findViewById(R.id.odlt_tv_datebg);
        odlt_tv_dateed = itemView.findViewById(R.id.odlt_tv_dateed);
        odlt_tv_status = itemView.findViewById(R.id.odlt_tv_status);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (mListener != null) {
            mListener.onClick(v);
        }
    }
}
