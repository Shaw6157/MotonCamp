package com.ais.mnc.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.OrderBean;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.ilistener.IItemClickListener;

import java.util.List;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 29/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderListViewHolder> {
    Context mContext;
    List<OrderBean> orderList;
    IItemClickListener itemClickListener;

    public OrderAdapter(Context context, List<OrderBean> orderList) {
        mContext = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View itemView = LayoutInflater.from(mContext).inflate(R.layout.order_list_layout, viewGroup, false);
//        return new OrderListViewHolder(itemView, itemClickListener);

        return new OrderListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.order_list_layout, viewGroup, false), itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder orderListViewHolder, int i) {
        OrderBean currentOrder = orderList.get(i);

        orderListViewHolder.odlt_tv_amount.setText(currentOrder.getAmount() + "");
        orderListViewHolder.odlt_tv_vname.setText(currentOrder.getVid() + "");
        orderListViewHolder.odlt_tv_oid.setText(currentOrder.getOid() + "");
        orderListViewHolder.odlt_tv_datebg.setText(currentOrder.getDatebg() + "");
        orderListViewHolder.odlt_tv_dateed.setText(currentOrder.getDateed() + "");
        orderListViewHolder.odlt_tv_status.setText(MncUtilities.getStatusTxt(currentOrder.getOstate()) + "");

        //TODO set long click
        //then show cancel dialog
        //update the order

        //using  orderList.get(i) in the method
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
