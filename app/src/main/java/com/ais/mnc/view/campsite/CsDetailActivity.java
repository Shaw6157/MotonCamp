package com.ais.mnc.view.campsite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.dao.PhotoDao;
import com.ais.mnc.db.daoimp.PhotoDaoImp;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.system.WebPageActivity;

public class CsDetailActivity extends AppCompatActivity {
    private static final String TAG = "CsDetailActivity >>> ";

    TextView csdt_tv_name, csdt_tv_url, csdt_tv_address, csdt_tv_phone, csdt_tv_features, csdt_tv_info;
    ImageView csdt_img_title, csdt_photo1, csdt_photo2;
    Button csdt_btn_phone, csdt_btn_mmessage, csdt_btn_more;

    CampBean mCurrentCpsite;
    PhotoDao mPhotoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campsite_detail);

        mCurrentCpsite = MncUtilities.currentCpsite;

        if (mCurrentCpsite == null) {
            MncUtilities.toastMessage(this, "loading error!");
            MncUtilities.startNextActivity(this, CsListActivity.class, true);
            return;
        }

        Log.d(TAG, "cp detail :" + mCurrentCpsite.getCname());

        initView();

        initData();

    }

    private void initData() {
        mPhotoDao = new PhotoDaoImp(this);

        csdt_tv_name.setText(mCurrentCpsite.getCname());
//        csdt_tv_url.setText(mCurrentCpsite.getUrl());
        csdt_tv_address.setText(mCurrentCpsite.getAddress());
        csdt_tv_phone.setText(mCurrentCpsite.getPhone());
        csdt_tv_features.setText(mCurrentCpsite.getFeatures());
        csdt_tv_info.setText(mCurrentCpsite.getInfo());

        csdt_tv_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MncUtilities.startNextActivity(CsDetailActivity.this, WebPageActivity.class, mCurrentCpsite.getUrl(), false);
            }
        });

        //set campsite images
        MncUtilities.setMncImage(this, mCurrentCpsite.getImage(), csdt_img_title);

        //set photos related to this campsite
        MncUtilities.currentPhotoList = mPhotoDao.findByCID(mCurrentCpsite.getCid());
        if (MncUtilities.currentPhotoList == null) {
            csdt_btn_more.setText("no more!");
            csdt_btn_more.setClickable(false);
            return;
        }
        MncUtilities.setMncImage(this, MncUtilities.currentPhotoList.get(0).getPath(), csdt_photo1);

        if (MncUtilities.currentPhotoList.size() < 2) {
            csdt_btn_more.setText("no more!");
            csdt_btn_more.setClickable(false);
            return;
        }
        csdt_btn_more.setClickable(true);
        MncUtilities.setMncImage(this, MncUtilities.currentPhotoList.get(1).getPath(), csdt_photo2);
    }

    private void initView() {
        csdt_tv_name = findViewById(R.id.csdt_tv_name);
        csdt_tv_url = findViewById(R.id.csdt_tv_url);
        csdt_tv_address = findViewById(R.id.csdt_tv_address);
        csdt_tv_phone = findViewById(R.id.csdt_tv_phone);
        csdt_tv_features = findViewById(R.id.csdt_tv_features);
        csdt_tv_info = findViewById(R.id.csdt_tv_info);

        csdt_img_title = findViewById(R.id.csdt_img_title);
        csdt_photo1 = findViewById(R.id.csdt_photo1);
        csdt_photo2 = findViewById(R.id.csdt_photo2);

        csdt_btn_phone = findViewById(R.id.csdt_btn_phone);
        csdt_btn_mmessage = findViewById(R.id.csdt_btn_message);
        csdt_btn_more = findViewById(R.id.csdt_btn_more);

        csdt_btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(CsDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CsDetailActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
//                    MncUtilities.toastMessage(CsDetailActivity.this, "phone call permission not granted.");
                    return;
                }
                Uri uriCall = Uri.parse("tel:" + csdt_tv_phone.getText());
                startActivity(new Intent(Intent.ACTION_CALL, uriCall));
            }
        });

        csdt_btn_mmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriMsg = Uri.parse("smsto:" + csdt_tv_phone.getText());
                startActivity(new Intent(Intent.ACTION_VIEW, uriMsg));
            }
        });

        csdt_btn_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MncUtilities.startNextActivity(CsDetailActivity.this, PhotoListActivity.class, false);
            }
        });

    }
}
