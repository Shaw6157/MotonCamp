package com.ais.mnc.view.motorhome;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.OrderBean;
import com.ais.mnc.db.bean.VehicleBean;
import com.ais.mnc.db.dao.OrderDao;
import com.ais.mnc.db.dao.VehicleDao;
import com.ais.mnc.db.daoimp.OrderDaoImp;
import com.ais.mnc.db.phpimp.OrderTask;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.system.UserLoginActivity;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class VehicleDetailActivity extends AppCompatActivity {
    private static final String TAG = "VDetail Activity >>>";

    //for vehicle info
    TextView vdtl_tv_name;
    TextView vdtl_tv_type;
    TextView vdtl_tv_transm;
    TextView vdtl_tv_year;
    TextView vdtl_tv_engine;
    TextView vdtl_tv_price;
    TextView vdtl_tv_info;
    TextView vdtl_tv_model;

    TabHost tabhost;
    ImageView vdtl_img;
    TextView vd_tv_name,
            vd_tv_type,
            vd_tv_year;

    FloatingActionButton vd_fab;

    VehicleBean currentVehicle;
    VehicleDao mVehicleDao;
    OrderDao mOrderDao;

    //order dialog elements
    View orderView;
    ImageView vlst_img;
    TextView vlst_tv_name,
            vlst_tv_amount,
            vlst_tv_type,
            vlst_tv_transmission,
            vlst_tv_engine,
            od_et_days;

    EditText od_et_title;
    EditText od_et_fname;
    EditText od_et_lname;
    EditText od_et_phone;
    EditText od_et_datebg;
    EditText od_et_dateed;
    TextView od_et_amount;

    CheckBox od_check;

    Calendar c_datebg = Calendar.getInstance();
    Calendar c_dateed = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);

        //draw the Toolbar and Tabs
        initTabs();

        //init view elements on the activity screen
        initView();

        currentVehicle = MncUtilities.currentVehicle;

        if (currentVehicle != null) {
            Log.d(TAG, "vehicle got : " + currentVehicle.getVname());

            //set detail values
            Picasso.with(this)
                    .load(currentVehicle.getImage())
                    .into(vdtl_img);

//            vd_tv_name.setText(currentVehicle.getVname());
//            vd_tv_type.setText(currentVehicle.getType() + " Berth");

            vdtl_tv_name.setText(currentVehicle.getVname());
            vdtl_tv_type.setText(currentVehicle.getType() + " Berth");
            vdtl_tv_transm.setText(currentVehicle.getTransmission());
            vdtl_tv_year.setText(currentVehicle.getYear());
            vdtl_tv_engine.setText(currentVehicle.getEngin());
            vdtl_tv_price.setText(currentVehicle.getPrice() + "");
            vdtl_tv_info.setText(currentVehicle.getInfo());
            vdtl_tv_model.setText(currentVehicle.getModel());

            //set float action button
            vd_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MncUtilities.currentUser != null) {
                        showOrderDialog();
                    } else {
                        MncUtilities.toastMessage(VehicleDetailActivity.this, "You must login before make booking!");
                        MncUtilities.previousClass = VehicleDetailActivity.class;
                        MncUtilities.startNextActivity(VehicleDetailActivity.this, UserLoginActivity.class, false);
                    }
                }
            });
        }
    }

    private void initView() {
        vdtl_img = findViewById(R.id.vdtl_img_top);
//        vd_tv_name = findViewById(R.id.vd_tv_vname);
//        vd_tv_type = findViewById(R.id.vd_tv_type);
//        vd_tv_year = findViewById(R.id.vd_tv_year);
        vd_fab = findViewById(R.id.vdtl_fab);

        vdtl_tv_name = findViewById(R.id.vdtl_tv_name);
        vdtl_tv_type = findViewById(R.id.vdtl_tv_type);
        vdtl_tv_transm = findViewById(R.id.vdtl_tv_transm);
        vdtl_tv_year = findViewById(R.id.vdtl_tv_year);
        vdtl_tv_engine = findViewById(R.id.vdtl_tv_engine);
        vdtl_tv_price = findViewById(R.id.vdtl_tv_price);
        vdtl_tv_info = findViewById(R.id.vdtl_tv_info);
        vdtl_tv_model = findViewById(R.id.vdtl_tv_model);
    }

    private void initTabs() {
        //init TabHost
        tabhost = findViewById(R.id.vdtl_tabhost);
        tabhost.setup();

        //Tab1
        String title_tab1 = getResources().getString(R.string.vdetail_tab1);
        TabHost.TabSpec spec =
                tabhost.newTabSpec(title_tab1)
                        .setContent(R.id.vdtl_tab1)
                        .setIndicator(title_tab1);
        tabhost.addTab(spec);

        //Tab2
        String title_tab2 = getResources().getString(R.string.vdetail_tab2);
        spec = tabhost.newTabSpec(title_tab2)
                .setContent(R.id.vdtl_tab2)
                .setIndicator(title_tab2);
        tabhost.addTab(spec);

        //set click animation Event
        tabhost.setOnTabChangedListener(new VehicleDetailAnimation(this, tabhost));
    }

    private void showOrderDialog() {
        Log.d(TAG, "dialog here... init...  ");

        orderView = LayoutInflater.from(this)
                .inflate(R.layout.order_add_layout, null);
        initNewOrderDialog();

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setView(orderView);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!od_check.isChecked()) {
                    MncUtilities.toastMessage(VehicleDetailActivity.this, "Please agree the terms first");
                } else {
                    OrderBean newOrder = new OrderBean(
                            1,
                            MncUtilities.currentVehicle.getVid(),
                            MncUtilities.currentUser.getUid(),
                            od_et_datebg.getText() + "",
                            od_et_dateed.getText() + "",
                            Integer.parseInt(od_et_amount.getText() + ""),
                            od_et_days.getText().toString(),
                            "10",
                            "" + od_et_fname.getText() + od_et_lname.getText(),
                            od_et_phone.getText() + ""
                    );

                    //1. local version
//                    mOrderDao = new OrderDaoImp(VehicleDetailActivity.this);
//                    mOrderDao.createOrder(newOrder);
//                    MncUtilities.toastMessage(VehicleDetailActivity.this, "Order submited!");
//                    dialog.dismiss();

                    //2. online version
                    new OrderTask("createorder", VehicleDetailActivity.this, dialog).execute(newOrder);

                }
            }
        });
        builder.show();
    }

    private void initNewOrderDialog() {
        //set elements on the layout xml file

        vlst_img        = orderView.findViewById(R.id.vlst_img);
        vlst_tv_name    = orderView.findViewById(R.id.vlst_tv_name);
        vlst_tv_amount  = orderView.findViewById(R.id.vlst_tv_amount);
        vlst_tv_type    = orderView.findViewById(R.id.vlst_tv_type);
        vlst_tv_transmission = orderView.findViewById(R.id.vlst_tv_transmission);
        vlst_tv_engine  = orderView.findViewById(R.id.vlst_tv_engine);
        od_et_days      = orderView.findViewById(R.id.od_et_days);

        od_et_title = orderView.findViewById(R.id.od_et_title);
        od_et_fname = orderView.findViewById(R.id.od_et_fname);
        od_et_lname = orderView.findViewById(R.id.od_et_lname);
        od_et_phone = orderView.findViewById(R.id.od_et_phone);
        od_et_datebg = orderView.findViewById(R.id.od_et_datebg);
        od_et_dateed = orderView.findViewById(R.id.od_et_dateed);
        od_et_amount = orderView.findViewById(R.id.od_et_amount);

        od_check = orderView.findViewById(R.id.od_check);

        // TODO set default date
//        od_et_datebg.set

        od_et_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MncUtilities.showTitleDialog(VehicleDetailActivity.this, (TextView) od_et_title);
            }
        });

        setListener();

        MncUtilities.setMncImage(this, MncUtilities.currentVehicle.getImage(), vlst_img);
        vlst_tv_name.setText("" + MncUtilities.currentVehicle.getVname());
        vlst_tv_amount.setText("$" + MncUtilities.currentVehicle.getPrice());
        vlst_tv_type.setText(" - " + MncUtilities.currentVehicle.getType() + " Berth");
        vlst_tv_transmission.setText(" - " + MncUtilities.currentVehicle.getTransmission());
        vlst_tv_engine.setText(" - " + MncUtilities.currentVehicle.getEngin());

        od_et_days.setText("1");
        od_et_amount.setText("" + MncUtilities.currentVehicle.getPrice());

    }

    private void setListener(){
        od_et_datebg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(VehicleDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        c_datebg.set(year, month, dayOfMonth);
                        od_et_datebg.setText(dayOfMonth + "-" + (month + 1) +  "-" + year);
                        calculateDays(c_datebg, c_dateed);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        od_et_dateed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                DatePickerDialog dlg =  new DatePickerDialog(VehicleDetailActivity.this, null,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dlg.setOnDateSetListener(
                        new DatePickerDialog.OnDateSetListener() {
                             @Override
                             public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                 c_dateed.set(year, month, dayOfMonth);
                                 od_et_dateed.setText(dayOfMonth + "-" + (month + 1) +  "-" + year);
                                 calculateDays(c_datebg, c_dateed);
                             }
                         }
                );
                dlg.show();
            }
        });
    }

    private void calculateDays (Calendar c_bg, Calendar c_ed) {
        if (c_bg != null && c_ed != null) {
            long timebg = c_bg.getTimeInMillis();
            long timeed = c_ed.getTimeInMillis();
            if (timebg > timeed) {
                MncUtilities.toastMessage(VehicleDetailActivity.this, "Begin date must be earlier than END date");

            } else if (timebg == timeed) {

            } else {
                long longDays = (timeed - timebg) / (1000 * 3600 * 24);
                int intDays = Integer.parseInt(String.valueOf(longDays)) + 1;
                od_et_days.setText("" + intDays);
                od_et_amount.setText("" + (MncUtilities.currentVehicle.getPrice() * intDays));
                return;
            }
            od_et_days.setText("1");
            od_et_amount.setText("" + MncUtilities.currentVehicle.getPrice());
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "destroy and clear static vehicle...");
        MncUtilities.currentVehicle = null;
        super.onDestroy();
    }
}
