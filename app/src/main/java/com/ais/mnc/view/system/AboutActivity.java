package com.ais.mnc.view.system;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.ais.mnc.R;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.campsite.CsListActivity;

public class AboutActivity extends AppCompatActivity {

    Button btn_back;
    Toolbar mnc_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnc_about);

        //set toolbar
        mnc_toolbar = findViewById(R.id.mnc_toolbar);
        setSupportActionBar(mnc_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MncUtilities.startNextActivity(AboutActivity.this, CsListActivity.class, true);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
