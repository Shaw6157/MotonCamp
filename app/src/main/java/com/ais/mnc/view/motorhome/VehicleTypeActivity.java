package com.ais.mnc.view.motorhome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.VehicleBean;
import com.ais.mnc.db.dao.VehicleDao;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.VehicleCardAdapter;
import com.ais.mnc.view.campsite.CsListActivity;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

public class VehicleTypeActivity extends AppCompatActivity {
    private static final String TAG = "VehicleTypeActivity >>>";

    SliderLayout vlst_slider;
    RecyclerView recycle_vtypelist;
    Toolbar vtyp_toolbar;

    List<VehicleBean> vTypeList;
    VehicleDao lvVehicleDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_type);

        //set toolbar
        vtyp_toolbar = findViewById(R.id.vtyp_toolbar);
        setSupportActionBar(vtyp_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //get top deals for sliderview
        vlst_slider = findViewById(R.id.vtyp_slider);
        initDealSlider();

        //set vehicle types datA
        initVehicleTypes();

        //set vehicle types adpter
        recycle_vtypelist = findViewById(R.id.vtyp_lyt_recycle);
//        recycle_vtypelist.setLayoutManager(new GridLayoutManager(this, 2));
        recycle_vtypelist.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recycle_vtypelist.setHasFixedSize(true);
        recycle_vtypelist.setAdapter(new VehicleCardAdapter(this, vTypeList));

    }

    private void initDealSlider() {
        TextSliderView txtSliderView1 = new TextSliderView(this);
        txtSliderView1
                .description("Britz: 5% Long Hire Discount")
                .image(R.drawable.deal1)
//                .image("https://upload-images.jianshu.io/upload_images/2570030-a6c58e16d0b4f5d6.png")
                .setScaleType(BaseSliderView.ScaleType.Fit);
        txtSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                MncUtilities.toastMessage(VehicleTypeActivity.this, "first deal, coming soon...");
            }
        });

        TextSliderView txtSliderView2 = new TextSliderView(this);
        txtSliderView2.description("Apollo: Early Bird 5% Off")
                .image(R.drawable.deal2)
                .setScaleType(BaseSliderView.ScaleType.Fit);
        txtSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                MncUtilities.toastMessage(VehicleTypeActivity.this, "second deal, coming soon...");
            }
        });

        vlst_slider.addSlider(txtSliderView1);
        vlst_slider.addSlider(txtSliderView2);
    }

    private void initVehicleTypes() {
//        //CAN BE FROM DB, BUT DIDNOT
//        lvVehicleDao = new VehicleDaoImp(this);
//        vTypeList = lvVehicleDao.getAllTypes();
        vTypeList = new ArrayList<VehicleBean>(5);
        VehicleBean vehicleType1 = new VehicleBean();
        vehicleType1.setVname("Sleeps 2");
        vehicleType1.setImage("https://www.gonewiththewynns.com/wp-content/uploads/2012/08/airstream_interstate_exterior1.jpg");
        vehicleType1.setType("1");
        vTypeList.add(vehicleType1);

        VehicleBean vehicleType2 = new VehicleBean();
        vehicleType2.setVname("Sleeps 3");
        vehicleType2.setImage("https://cdn.britannica.com/s:700x450/07/126507-004-F381C588.jpg");
        vehicleType2.setType("3");
        vTypeList.add(vehicleType2);

        VehicleBean vehicleType3 = new VehicleBean();
        vehicleType3.setVname("Sleeps 4");
        vehicleType3.setImage("https://densmoreinsurance.com/wp-content/uploads/2018/08/recreational-1.jpg");
        vehicleType3.setType("4");
        vTypeList.add(vehicleType3);

        VehicleBean vehicleType4 = new VehicleBean();
        vehicleType4.setVname("Sleeps 5");
        vehicleType4.setImage("http://www.thatsnotcamping.com/wp-content/uploads/2011/07/motorhome_03.jpg");
        vehicleType4.setType("5");
        vTypeList.add(vehicleType4);

        VehicleBean vehicleType5 = new VehicleBean();
        vehicleType5.setVname("Sleeps 6 or more");
        vehicleType5.setImage("https://www.calgaryparking.com/documents/10184/11467/rv.png/7e4d5ffa-b968-459b-bd5b-bfb86e672081?t=1394137749257");
        vehicleType5.setType("6");
        vTypeList.add(vehicleType5);

        VehicleBean vehicleType6 = new VehicleBean();
        vehicleType6.setVname("Check All");
        vehicleType6.setImage("https://www.boydcorp.com/images/vehicle/11.png");
        vehicleType6.setType("9");
        vTypeList.add(vehicleType6);
    }

    @Override
    protected void onDestroy() {
        vlst_slider.stopAutoCycle();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        MncUtilities.startNextActivity(VehicleTypeActivity.this, CsListActivity.class, false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
