package com.ais.mnc.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.bean.PhotoBean;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.ilistener.IItemClickListener;
import com.ais.mnc.view.campsite.CsDetailActivity;
import com.ais.mnc.view.campsite.PhotoListActivity;
import com.bumptech.glide.Glide;
import com.jelly.mango.ImageSelectListener;
import com.jelly.mango.Mango;
import com.jelly.mango.MultiplexImage;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 29/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {
    private static final String TAG = "PhotoListAdapter >>>";

    List<MultiplexImage> mPhotoList;
    Context context;
    IItemClickListener itemClickListener;

    public PhotoListAdapter(Context context, List<PhotoBean> photoList) {
        this.context = context;

        mPhotoList = new ArrayList<MultiplexImage>();
        for (PhotoBean eachPhoto : photoList) {
            mPhotoList.add(new MultiplexImage(null, eachPhoto.getPath(), MultiplexImage.ImageType.NORMAL));
        }
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PhotoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.photo_list_layout, viewGroup, false), itemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder photoViewHolder, final int i) {
//        Glide.with(context).load(mPhotoList.get(i).getTPath()).thumbnail(1f).into(photoViewHolder.image);
        MncUtilities.setMncImage(context, mPhotoList.get(i).getOPath(), photoViewHolder.image);

        photoViewHolder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Mango.setImages(mPhotoList);
                Mango.setPosition(i);
                Mango.setImageSelectListener(new ImageSelectListener() {
                    @Override
                    public void select(int index) {
                        Log.d("Mango >>>>>>>>>", "select: "+index);
                    }
                });
                try {
                    Mango.open(context);
                }catch (Exception e){
                    Log.d(TAG, "ALBUM ERROR : " + e.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        IItemClickListener itemClickListener;

        public PhotoViewHolder(View itemView, IItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            image = itemView.findViewById(R.id.ptlt_image);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(IItemClickListener pItemClickListener) {
            itemClickListener = pItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null) {
                itemClickListener.onClick(v);
            }
        }

    }

}
