package com.ais.mnc.view.system;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.UserBean;
import com.ais.mnc.db.phpimp.UserTask;
import com.ais.mnc.util.MncUtilities;

import static com.ais.mnc.util.MncUtilities.*;

public class UserLoginActivity extends AppCompatActivity{
    private static final String TAG = "UserLoginActivity >>>";

    Toolbar toolbar;
    EditText email,password;
    CheckBox checkBox;
    ImageButton signin;
    Button signup;
    Toolbar user_login_toolbar;

    //Local SQLite
//    UserDao mUserDao;

    String lv_name;
    String lv_pwd;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        initView();

        //init toolbar
        user_login_toolbar = findViewById(R.id.user_login_toolbar);
        setSupportActionBar(user_login_toolbar);
        ActionBar lvActionBar = getSupportActionBar();
        lvActionBar.setDisplayHomeAsUpEnabled(true);

        //Local SQlite
//        mUserDao = new UserDaoImp(this);

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                lv_name = email.getText() + "";
                lv_pwd = password.getText() + "";
                if (MncUtilities.isEmptyTxt(lv_name) || MncUtilities.isEmptyTxt(lv_pwd)) {
                    toastMessage(UserLoginActivity.this, "Please enter your id or password! ");
                    return;
                }

                UserBean user = new UserBean(1, lv_name, lv_name, lv_pwd);

                new UserTask("signin", UserLoginActivity.this, checkBox).execute(user);

//                if (mUserDao.checkExist("", "")) {
//                    if (lv_pwd.equals(mUserDao.getPassword(lv_name))) {
//
//                        if (checkBox.isChecked()) {
//                            //save info for SharedPreferences
//                            pref = getSharedPreferences("MncUser", MODE_PRIVATE);
//                            editor = pref.edit();
//                            editor.putString("account", lv_name);
//                            editor.putString("password", lv_pwd);
//                            editor.putString("check", "1");
//                            editor.commit();
//                        } else {
//                            //clear info for SharedPreferences
//                            pref = getSharedPreferences("MncUser", MODE_PRIVATE);
//                            editor = pref.edit();
//                            editor.putString("account", "");
//                            editor.putString("password", "");
//                            editor.putString("check", "0");
//                            editor.commit();
//                        }
//
//                        toastMessage(UserLoginActivity.this, "Welcome back! " + lv_name);
//                        currentUser = mUserDao.findByName(lv_name);
//                        Log.d(TAG, "succ   UID:" + currentUser.getUid() + "   UNAME: " + lv_name);
//
//                        finish();
////                        Class lvPreviousClass = MncUtilities.previousClass;
////                        if (lvPreviousClass == null) {
////                            startNextActivity(UserLoginActivity.this, CsListActivity.class, true);
////                        } else {
////                            MncUtilities.previousClass = null;
////                            startNextActivity(UserLoginActivity.this, lvPreviousClass, true);
////                        }
//                    } else {
//                        toastMessage(UserLoginActivity.this, "Wrong password! ");
//                    }
//                } else {
//                    toastMessage(UserLoginActivity.this, "No such user! ");
//                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startNextActivity(UserLoginActivity.this, UserSignUpActivity.class, true);
            }
        });
    }

    private void initView() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        email = findViewById(R.id.et_uname);
        password = findViewById(R.id.et_password);
        checkBox = findViewById(R.id.checkbox);

        signin = findViewById(R.id.btn_signin);
        signup =  findViewById(R.id.btn_signup);

        pref = getSharedPreferences("MncUser", MODE_PRIVATE);
        if (pref!= null) {
            email.setText(pref.getString("account", ""));
            password.setText(pref.getString("password", ""));
            checkBox.setChecked("1".equals(pref.getString("check", "")));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}