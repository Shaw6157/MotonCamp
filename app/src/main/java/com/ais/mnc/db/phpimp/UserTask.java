package com.ais.mnc.db.phpimp;

import android.os.AsyncTask;
import android.util.Log;

import com.ais.mnc.db.bean.UserBean;
import com.ais.mnc.util.MncUtilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 25/11/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class UserTask extends AsyncTask <UserBean, Void, String> {
    private String mAction;

    public UserTask(String action) {
        this.mAction = action;
    }

    @Override
    protected String doInBackground(UserBean... users) {
        StringBuilder sb = new StringBuilder("");
        try {
//                URL url = new URL(ConfServer.SERVER_LOCAL + "phpSaveMemo.php");
            URL url = new URL(ConfServer.SERVER_CONF + "operation_user.php");
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

                Log.d("param >>>>", param);
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
            Log.d("users:", sb.toString());
        } catch (Exception e) {
            Log.d("error >>>>", e.getMessage());
            return "";
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String users) {
        super.onPostExecute(users);

        try {
            JSONArray array = new JSONArray(users);
            for (int i = 0; i < array.length() - 1; i++) {
                JSONObject obj = array.getJSONObject(i);

                Log.d("dsfs>>>>>>", obj.toString());

                String str_id = obj.getString("memoID");

            }

        } catch (Exception e) {
            Log.d("error>>>", "" + e.getMessage());
        }
    }
}
