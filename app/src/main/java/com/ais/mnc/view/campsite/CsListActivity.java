package com.ais.mnc.view.campsite;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.bean.OrderBean;
import com.ais.mnc.db.bean.UserBean;
import com.ais.mnc.db.phpimp.CampsiteTask;
import com.ais.mnc.db.phpimp.UserTask;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.motorhome.OrderListActivity;
import com.ais.mnc.view.motorhome.VehicleTypeActivity;
import com.ais.mnc.view.system.AboutActivity;
import com.ais.mnc.view.system.SplashActivity;
import com.ais.mnc.view.system.UserLoginActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CsListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "CsListActivity >>> ";

    RecyclerView recycle_clist;
    TextView dwr_tv_uid, dwr_tv_email;
    ImageView dwr_img_head;

    //for dialog
    TextInputEditText user_txt_uname;
    TextInputEditText user_txt_email;
    TextInputEditText user_txt_email_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campsite_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set user info to drawer
        View headerView = navigationView.getHeaderView(0);
        dwr_tv_uid = headerView.findViewById(R.id.dwr_tv_uid);
        dwr_tv_email = headerView.findViewById(R.id.dwr_tv_email);
        dwr_img_head = headerView.findViewById(R.id.dwr_img_head);

        dwr_img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MncUtilities.currentUser != null) {
//                    MncUtilities.toastMessage(CsListActivity.this, "already logged in!");
                    showProfileDialog();

                } else {
                    MncUtilities.startNextActivity(CsListActivity.this, UserLoginActivity.class, false);
                }
            }
        });

        //set icon and user name
        initDrawUser();

        //set floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MncUtilities.startNextActivity(CsListActivity.this, CsMapActivity.class, false);
            }
        });

        //1. local version
//        //set context campsite list
//        MncUtilities.currentCampList = new CampsiteDaoImp(this).findAll();
//        recycle_clist = findViewById(R.id.clst_lyt_recycle);
//        recycle_clist.setLayoutManager(new GridLayoutManager(this, 2));
//        recycle_clist.setHasFixedSize(true);
//        recycle_clist.setAdapter(
//                new CampsiteListAdapter(
//                        this, MncUtilities.currentCampList));
        //2. online version
        new CampsiteTask("findall", this).execute(new CampBean());


        //facebook sharing
        printKeyHash();
    }

    private void showProfileDialog() {
        View profileView = LayoutInflater.from(this)
                .inflate(R.layout.user_profile_layout, null);

        user_txt_uname = profileView.findViewById(R.id.user_txt_uname);
        user_txt_email = profileView.findViewById(R.id.user_txt_email);
        user_txt_email_new = profileView.findViewById(R.id.user_txt_email_new);

        user_txt_uname.setText(MncUtilities.currentUser.getUname());
        user_txt_email.setText(MncUtilities.currentUser.getEmail());

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(profileView);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserBean updateUser = MncUtilities.currentUser;
                updateUser.setEmail(user_txt_email_new.getText() + "");
                new UserTask("updateuser", CsListActivity.this).execute(updateUser);
            }
        });
        builder.show();
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.ais.mnc",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
    }

    private void initDrawUser() {
        if (MncUtilities.currentUser == null) {
            dwr_tv_uid.setText("Guest");
            dwr_tv_email.setText("Click the icon to Login");
        } else {
            dwr_tv_uid.setText(MncUtilities.currentUser.getUname());
            dwr_tv_email.setText(MncUtilities.currentUser.getEmail());
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "  RESUMM reset the draw user");
        initDrawUser();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO no need currently Shaw
//        getMenuInflater().inflate(R.menu.sidemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            MncUtilities.toastMessage(this, "Not done yet.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_vehicle) {
            MncUtilities.startNextActivity(this, VehicleTypeActivity.class, false);
        } else if (id == R.id.nav_campsite) {
            MncUtilities.startNextActivity(this, CsListActivity.class, false);
        } else if (id == R.id.nav_cp_map) {
            MncUtilities.startNextActivity(this, CsMapActivity.class, false);
        } else if (id == R.id.nav_order) {
            if (MncUtilities.currentUser != null) {
                MncUtilities.startNextActivity(this, OrderListActivity.class, false);
            } else {
                MncUtilities.previousClass = OrderListActivity.class;
                MncUtilities.startNextActivity(this, UserLoginActivity.class, false);
            }
        } else if (id == R.id.nav_about) {
            MncUtilities.startNextActivity(this, AboutActivity.class, false);
        } else if (id == R.id.nav_logout) {
            if (MncUtilities.currentUser == null) {
                MncUtilities.toastMessage(this, "Haven't logged in yet!");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Log out");
                builder.setMessage("Do you want to logout ? ");

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ;
                    }
                });

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MncUtilities.currentUser = null;
                        Intent intent = new Intent(CsListActivity.this, SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
