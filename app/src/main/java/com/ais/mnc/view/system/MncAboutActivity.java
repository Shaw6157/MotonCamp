package com.ais.mnc.view.system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ais.mnc.R;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.campsite.CsListActivity;

public class MncAboutActivity extends AppCompatActivity {

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnc_about);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MncUtilities.startNextActivity(MncAboutActivity.this, CsListActivity.class, true);
            }
        });
    }
}
