package com.ais.mnc.view.motorhome;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.system.SplashActivity;

import java.util.Calendar;

public class OrderDetailActivity extends AppCompatActivity {

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
    Calendar c_datebg, c_dateed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        vlst_img = findViewById(R.id.vlst_img);
        vlst_tv_name = findViewById(R.id.vlst_tv_name);
        vlst_tv_amount = findViewById(R.id.vlst_tv_amount);
        vlst_tv_type = findViewById(R.id.vlst_tv_type);
        vlst_tv_transmission = findViewById(R.id.vlst_tv_transmission);
        vlst_tv_engine = findViewById(R.id.vlst_tv_engine);
        od_et_days = findViewById(R.id.od_et_days);

        od_et_title = findViewById(R.id.od_et_title);
        od_et_fname = findViewById(R.id.od_et_fname);
        od_et_lname = findViewById(R.id.od_et_lname);
        od_et_phone = findViewById(R.id.od_et_phone);
        od_et_datebg = findViewById(R.id.od_et_datebg);
        od_et_dateed = findViewById(R.id.od_et_dateed);
        od_et_amount = findViewById(R.id.od_et_amount);

        od_et_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MncUtilities.showTitleDialog(OrderDetailActivity.this, (TextView) od_et_title);
            }
        });

        MncUtilities.setMncImage(this, MncUtilities.currentVehicle.getImage(), vlst_img);
        vlst_tv_name.setText("" + MncUtilities.currentVehicle.getVname());
        vlst_tv_amount.setText("$" + MncUtilities.currentVehicle.getPrice());
        vlst_tv_type.setText(" - " + MncUtilities.currentVehicle.getType() + " Berth");
        vlst_tv_transmission.setText(" - " + MncUtilities.currentVehicle.getTransmission());
        vlst_tv_engine.setText(" - " + MncUtilities.currentVehicle.getEngin());

        od_et_days.setText("1");
        od_et_amount.setText("" + MncUtilities.currentVehicle.getPrice());

        od_et_datebg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    showDateDialog(OrderDetailActivity.this, od_et_datebg);

                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(OrderDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            c_datebg.set(year, month, dayOfMonth);
                            od_et_datebg.setText(dayOfMonth + "-" + (month + 1) +  "-" + year);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                } else {
                    calculateDays(c_datebg, c_dateed);
                }

            }
        });

        od_et_dateed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }

    private void calculateDays (Calendar c_bg, Calendar c_ed) {
        if (c_bg != null && c_ed != null) {
            long timebg = c_bg.getTimeInMillis();
            long timeed = c_ed.getTimeInMillis();
            if (timebg > timeed) {
                MncUtilities.toastMessage(OrderDetailActivity.this, "Begin date must be earlier than END date");

            } else if (timebg == timeed) {

            } else {
                long longDays = (timeed - timebg) / (1000 * 3600 * 24);
                int intDays = Integer.parseInt(String.valueOf(longDays));
                od_et_days.setText("" + intDays);
                od_et_amount.setText("$" + (MncUtilities.currentVehicle.getPrice() * intDays));
                return;
            }
            od_et_days.setText("1");
            od_et_amount.setText("$" + MncUtilities.currentVehicle.getPrice());
        }
    }

}
