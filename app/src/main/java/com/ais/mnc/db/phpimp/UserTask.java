package com.ais.mnc.db.phpimp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.CheckBox;

import com.ais.mnc.db.bean.UserBean;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.system.UserLoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Context.MODE_PRIVATE;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 25/11/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class UserTask extends AsyncTask<UserBean, Void, String> {
    private static final String TAG = "UserTask >>> ";

    private String mAction;
    private Context mContext;
    private CheckBox mCheckBox;

    private UserBean[] mUserBeans;

    public UserTask(String action) {
        this.mAction = action;
    }

    public UserTask(String action, Context context, CheckBox checkBox) {
        this.mAction = action;
        this.mContext = context;
        this.mCheckBox = checkBox;
    }

    @Override
    protected String doInBackground(UserBean... users) {
        StringBuilder sb = new StringBuilder("");
        mUserBeans = users;
        try {
//                URL url = new URL(ConfServer.SERVER_LOCAL + "phpSaveMemo.php");
            URL url = new URL(ConfServer.SERVER_CONF + "function_user.php");
            URLConnection lvConnection = url.openConnection();

            //$action_validation = ['createUser', 'getPassword', 'findByName', 'findAll', 'updateUser', 'deleteUser', 'checkExist'];
            if (!"findAll".equals(mAction)) {
                String param = MncUtilities.encodeUTF8("action") + "="
                        + MncUtilities.encodeUTF8(mAction) + "&"
                        + MncUtilities.encodeUTF8("uname") + "="
                        + MncUtilities.encodeUTF8(users[0].getUname()) + "&"
                        + MncUtilities.encodeUTF8("email") + "="
                        + MncUtilities.encodeUTF8(users[0].getEmail()) + "&"
                        + MncUtilities.encodeUTF8("password") + "="
                        + MncUtilities.encodeUTF8(users[0].getPassword()) + "&"
                        + MncUtilities.encodeUTF8("usertype") + "="
                        + MncUtilities.encodeUTF8("user");


//                        URLEncoder.encode("action", ConfServer.SERVER_ENC) + "="
//                        + URLEncoder.encode(mAction, ConfServer.SERVER_ENC) + "&"
//                        + URLEncoder.encode("memoID", ConfServer.SERVER_ENC) + "="
//                        + URLEncoder.encode("" + users[0].getUname(), ConfServer.SERVER_ENC) + "&"
//                        + URLEncoder.encode("memoInfo", "UTF-8") + "="
//                        + URLEncoder.encode("" + users[0].getEmail(), ConfServer.SERVER_ENC) + "&"
//                        + URLEncoder.encode("memoType", ConfServer.SERVER_ENC) + "="
//                        + URLEncoder.encode("" + users[0].getPassword(), ConfServer.SERVER_ENC) + "&"
//                        + URLEncoder.encode("memoDate", ConfServer.SERVER_ENC) + "="
//                        + URLEncoder.encode("user", ConfServer.SERVER_ENC);

                Log.d(TAG, "param: " + param);
                lvConnection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(lvConnection.getOutputStream());
                writer.write(param);
                writer.flush();
            }

            //read data
            BufferedReader reader = new BufferedReader(new InputStreamReader(lvConnection.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Log.d(TAG, "echo:" + sb.toString());
        } catch (Exception e) {
            Log.d(TAG, "background error msg:" + e.getMessage());
            return "";
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String php_echo_str) {
        super.onPostExecute(php_echo_str);

        Log.d(TAG, "mAction     =" + mAction);
        Log.d(TAG, "php_echo_str=" + php_echo_str);

        switch (mAction) {
            case "signin":
                SharedPreferences pref;
                SharedPreferences.Editor editor;

                try {
                    JSONObject obj = new JSONObject(php_echo_str);

                    if (obj.optBoolean("sqlflag")) {
                        MncUtilities.currentUser = mUserBeans[0];
                        MncUtilities.toastMessage(mContext, "Welcome back " + mUserBeans[0].getUname() + "!");

                        //todo thread?
                        if (mCheckBox.isChecked()) {
                            //save info for SharedPreferences
                            pref = mContext.getSharedPreferences("MncUser", MODE_PRIVATE);
                            editor = pref.edit();
                            editor.putString("account", mUserBeans[0].getUname());
                            editor.putString("password", mUserBeans[0].getPassword());
                            editor.putString("check", "1");
                            editor.commit();
                        } else {
                            //clear info for SharedPreferences
                            pref = mContext.getSharedPreferences("MncUser", MODE_PRIVATE);
                            editor = pref.edit();
                            editor.putString("account", "");
                            editor.putString("password", "");
                            editor.putString("check", "0");
                            editor.commit();
                        }


                        ((Activity) mContext).finish();
//                        Class lvPreviousClass = MncUtilities.previousClass;
//                        if (lvPreviousClass == null) {
//                            startNextActivity(UserLoginActivity.this, CsListActivity.class, true);
//                        } else {
//                            MncUtilities.previousClass = null;
//                            startNextActivity(UserLoginActivity.this, lvPreviousClass, true);
//                        }

                    } else {
                        MncUtilities.toastMessage(mContext, "" + obj.optString("message"));
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "postexe error msg:" + e.getMessage());
                }

                break;
            case "findall":

                try {
                    JSONArray array = new JSONArray(php_echo_str);
                    for (int i = 0; i < array.length() - 1; i++) {
                        JSONObject obj = array.getJSONObject(i);

                        Log.d(TAG, obj.toString());

                        String str_id = obj.getString("sqlflag");
                        Log.d(TAG, str_id);

                    }

                } catch (JSONException e) {
                    Log.d(TAG, "postexe error msg:" + e.getMessage());
                }

                break;
            case "b":
                break;
            default:
                break;
        }

    }
}
