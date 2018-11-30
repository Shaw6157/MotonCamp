package com.ais.mnc.view.system;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.UserBean;
import com.ais.mnc.db.dao.UserDao;
import com.ais.mnc.db.daoimp.UserDaoImp;
import com.ais.mnc.db.phpimp.UserTask;
import com.ais.mnc.util.MncUtilities;

public class UserSignUpActivity extends AppCompatActivity{
    private static final String TAG = "UserSignUpActivity >>>";

    Toolbar toolbar;
    EditText email,password,name;
    CheckBox checkBox;
    ImageButton signup;
    Button signin;
    Toolbar user_signup_toolbar;

    UserDao mUserDao;
    UserBean signup_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        initView();

        //init toolbar
        user_signup_toolbar = findViewById(R.id.user_signup_toolbar);
        setSupportActionBar(user_signup_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mUserDao = new UserDaoImp(this);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String uname = name.getText() + "";
                String uemail = email.getText() + "";
                String upwd = password.getText() + "";

                //validations
                if (!checkBox.isChecked()) {
                    MncUtilities.toastMessage(UserSignUpActivity.this, "Please agree to our terms of service first !");
                    return;
                } else if (MncUtilities.isEmptyTxt(uname) || MncUtilities.isEmptyTxt(uemail) || MncUtilities.isEmptyTxt(upwd)) {
                    MncUtilities.toastMessage(UserSignUpActivity.this, "Please fill up all your info before sing up!");
                    return;
                } else if (upwd.length() < 6) {
                    MncUtilities.toastMessage(UserSignUpActivity.this, "The length of the password should be more than 5 !");
                    return;
                } else {

                }

                signup_user = new UserBean(1, uname, uemail, upwd);
                //not necessary for user_id, as the column is auto-increment,directly put 1 here.

                new UserTask("signup", UserSignUpActivity.this).execute(signup_user);

//                if (!mUserDao.checkExist(signup_user.getUname(), signup_user.getEmail())) {
//                    if (mUserDao.createUser(signup_user)) {
//                        Log.d(TAG, "created  user === " + signup_user.getUname());
//                        MncUtilities.toastMessage(UserSignUpActivity.this, "Sign up Successfully!");
//                        MncUtilities.startNextActivity(UserSignUpActivity.this, UserLoginActivity.class, true);
//                    } else {
//                        MncUtilities.toastMessage(UserSignUpActivity.this, "Sign up error!");
//                    }
//                } else {
//                    Log.d(TAG, "exists user === " + signup_user.getUname());
//                    MncUtilities.toastMessage(UserSignUpActivity.this, "The user or email already exists ! ");
//                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MncUtilities.startNextActivity(UserSignUpActivity.this, UserLoginActivity.class, true);
            }
        });
    }

    private void initView() {
//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        checkBox = findViewById(R.id.checkbox);

        signup =findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}