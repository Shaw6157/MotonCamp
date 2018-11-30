package com.ais.mnc.view.campsite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.bean.PhotoBean;
import com.ais.mnc.db.dao.PhotoDao;
import com.ais.mnc.db.phpimp.PhotoTask;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.system.WebPageActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class CsDetailActivity extends AppCompatActivity {
    private static final String TAG = "CsDetailActivity >>> ";

    TextView csdt_tv_name, csdt_tv_url, csdt_tv_address, csdt_tv_phone, csdt_tv_features, csdt_tv_info;
    ImageView csdt_img_title, csdt_photo1, csdt_photo2;
    Button csdt_btn_phone, csdt_btn_mmessage, csdt_btn_more;
    Toolbar csdt_toolbar;

    CampBean mCurrentCpsite;
    PhotoDao mPhotoDao;

    //facebook
    TextView csdt_tv_share_facebook;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId("281866435867452");
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_campsite_detail);

        //set toolbar
        csdt_toolbar = findViewById(R.id.csdt_toolbar);
        setSupportActionBar(csdt_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar lvActionBar = getSupportActionBar();
        lvActionBar.setDisplayHomeAsUpEnabled(true);

        mCurrentCpsite = MncUtilities.currentCpsite;

        if (mCurrentCpsite == null) {
            MncUtilities.toastMessage(this, "loading error!");
            MncUtilities.startNextActivity(this, CsListActivity.class, true);
            return;
        }

        Log.d(TAG, "cp detail :" + mCurrentCpsite.getCname());

        initView();

        initData();

        initFacebookShare();
    }

    private void initFacebookShare() {
        csdt_tv_share_facebook = findViewById(R.id.csdt_tv_share_facebook);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        csdt_tv_share_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        MncUtilities.toastMessage(CsDetailActivity.this, "Share successfully!");
                    }

                    @Override
                    public void onCancel() {
                        MncUtilities.toastMessage(CsDetailActivity.this, "Share cancel!");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        MncUtilities.toastMessage(CsDetailActivity.this, "Share error!");
                    }
                });

                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("This is an awesome campsite!")
                        .setContentUrl(Uri.parse(mCurrentCpsite.getUrl()))
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(linkContent);
                }
            }
        });
    }

    private void initView() {
        csdt_tv_name = findViewById(R.id.csdt_tv_name);
        csdt_tv_url = findViewById(R.id.csdt_tv_url);
        csdt_tv_address = findViewById(R.id.csdt_tv_address);
        csdt_tv_phone = findViewById(R.id.csdt_tv_phone);
        csdt_tv_features = findViewById(R.id.csdt_tv_features);
        csdt_tv_info = findViewById(R.id.csdt_tv_info);
        csdt_toolbar = findViewById(R.id.csdt_toolbar);

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
                    ActivityCompat.requestPermissions(CsDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
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

        csdt_btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MncUtilities.startNextActivity(CsDetailActivity.this, PhotoListActivity.class, false);
            }
        });

    }

    private void initData() {
        csdt_toolbar.setTitle(mCurrentCpsite.getCname());
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
        MncUtilities.loadOnlineImage(this, mCurrentCpsite.getImage(), csdt_img_title);

        //set photos related to this campsite
//        //1. LOCAL VERSION
//        mPhotoDao = new PhotoDaoImp(this);
//        MncUtilities.currentPhotoList = mPhotoDao.findByCID(mCurrentCpsite.getCid());
//
//        if (MncUtilities.currentPhotoList == null) {
//            csdt_btn_more.setText("no more!");
//            csdt_btn_more.setClickable(false);
//            return;
//        }
//        MncUtilities.setMncImage(this, MncUtilities.currentPhotoList.get(0).getPath(), csdt_photo1);
//
//        if (MncUtilities.currentPhotoList.size() < 2) {
//            csdt_btn_more.setText("no more!");
//            csdt_btn_more.setClickable(false);
//            return;
//        }
//        csdt_btn_more.setClickable(true);
//        MncUtilities.setMncImage(this, MncUtilities.currentPhotoList.get(1).getPath(), csdt_photo2);

        //2. ONLINE VERSION
        PhotoBean searchBean = new PhotoBean();
        searchBean.setCid(mCurrentCpsite.getCid());
        new PhotoTask("findbycamp", this).execute(searchBean);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
