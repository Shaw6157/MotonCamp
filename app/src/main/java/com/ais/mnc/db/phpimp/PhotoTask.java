package com.ais.mnc.db.phpimp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.PhotoBean;
import com.ais.mnc.db.constant.TableConstant;
import com.ais.mnc.db.daoimp.PhotoDaoImp;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.PhotoListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 30/11/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class PhotoTask extends AsyncTask<PhotoBean, Void, String> {
    private static final String TAG = "PhotoTask >>> ";

    private String mAction;
    private Context mContext;
    private DialogInterface mDialog;

    ArrayList<PhotoBean> PhotoList; //deal with the result list
    RecyclerView recycle_plist;

    public PhotoTask(String action, Context context) {
        this.mAction = action;
        this.mContext = context;
    }

    @Override
    protected String doInBackground(PhotoBean... Photos) {
        StringBuilder sb = new StringBuilder("");
        try {
            URL url = new URL(ConfServer.SERVER_CONF + "function_photo.php");
            URLConnection lvConnection = url.openConnection();

            String param = MncUtilities.encodeUTF8("action") + "="
                    + MncUtilities.encodeUTF8(mAction);

            if (!"findall".equals(mAction)) {
//                $cid, $uid, $date, $path, $desc, $del
                param = param + "&"
                        + MncUtilities.encodeUTF8("cid") + "="
                        + MncUtilities.encodeUTF8(Photos[0].getCid()) + "&"
                        + MncUtilities.encodeUTF8("uid") + "="
                        + MncUtilities.encodeUTF8(Photos[0].getUid()) + "&"
                        + MncUtilities.encodeUTF8("date") + "="
                        + MncUtilities.encodeUTF8(Photos[0].getDate()) + "&"
                        + MncUtilities.encodeUTF8("path") + "="
                        + MncUtilities.encodeUTF8(Photos[0].getPath()) + "&"
                        + MncUtilities.encodeUTF8("desc") + "="
                        + MncUtilities.encodeUTF8(Photos[0].getDesc()) + "&"
                        + MncUtilities.encodeUTF8("del") + "=";

                if (Photos[0].getDel()) {
                    param += MncUtilities.encodeUTF8(1);
                } else {
                    param += MncUtilities.encodeUTF8(0);
                }
            }

            Log.d(TAG, "param: " + param);
            lvConnection.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(lvConnection.getOutputStream());
            writer.write(param);
            writer.flush();

            //read data
            BufferedReader reader = new BufferedReader(new InputStreamReader(lvConnection.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {

                sb.append(line);
            }
            if (sb != null) {
                Log.d(TAG, "101 echo:" + sb.toString());
            } else {
                Log.d(TAG, "103 echo:  nothing return to stringbuilder");
            }
        } catch (IOException e) {
            Log.d(TAG, "106 background error msg:" + e.getMessage());
            return "";
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String php_echo_str) {
        super.onPostExecute(php_echo_str);

        Log.d(TAG, "116 mAction  =" + mAction);
        Log.d(TAG, "php_echo_str =" + php_echo_str);

        if (php_echo_str.isEmpty()) {
            MncUtilities.toastMessage(mContext, "No data fetched!");
            return;
        }

        switch (mAction) {
            case "findall":
            case "findbycamp":
                //fetch data
                PhotoList = changeJsonToList(php_echo_str);
                //set into recyclerview
                Button csdt_btn_more = ((Activity) mContext).findViewById(R.id.csdt_btn_more);
                ImageView csdt_photo1 = ((Activity) mContext).findViewById(R.id.csdt_photo1);
                ImageView csdt_photo2 = ((Activity) mContext).findViewById(R.id.csdt_photo2);
                if (PhotoList != null && PhotoList.size() > 0) {
                    MncUtilities.currentPhotoList = PhotoList;

                    MncUtilities.loadOnlineImage(mContext, PhotoList.get(0).getPath(), csdt_photo1);

                    if (PhotoList.size() < 2) {
                        csdt_btn_more.setText("no more!");
                        csdt_btn_more.setClickable(false);
                        return;
                    }
                    csdt_btn_more.setClickable(true);
                    MncUtilities.loadOnlineImage(mContext, PhotoList.get(1).getPath(), csdt_photo2);

                } else {
                    csdt_btn_more.setText("no more!");
                    csdt_btn_more.setClickable(false);
                    MncUtilities.toastMessage(mContext, "No Photo data");
                }
                break;
            case "createPhoto":
                try {
                    JSONObject obj = new JSONObject(php_echo_str);
                    if (obj.optBoolean("sqlflag")) {
                        MncUtilities.toastMessage(mContext, obj.optString("message"));
                        if (mDialog != null) {
                            mDialog.dismiss();
                        }
                    } else {
                        MncUtilities.toastMessage(mContext, obj.optString("message"));
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "post-Photo error msg:" + e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    private ArrayList<PhotoBean> changeJsonToList(String str_json) {
        try {
            ArrayList<PhotoBean> list = new ArrayList<PhotoBean> ();
            JSONArray array = new JSONArray(str_json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.d(TAG, obj.toString());

//                $cid, $uid, $date, $path, $desc, $del
                PhotoBean eachBean = new PhotoBean();
                eachBean.setPid(obj.optInt(TableConstant.PHOTO_COL1_PID));
                eachBean.setCid(obj.optInt(TableConstant.PHOTO_COL2_CID));
                eachBean.setUid(obj.optInt(TableConstant.PHOTO_COL3_UID));
                eachBean.setDate(obj.optString(TableConstant.PHOTO_COL4_DATE));
                eachBean.setPath(obj.optString(TableConstant.PHOTO_COL5_PATH));
                eachBean.setDesc(obj.optString(TableConstant.PHOTO_COL6_DESC));
                eachBean.setDel(obj.optInt(TableConstant.PHOTO_COL7_DEL));

                list.add(eachBean);
            }
            return list;
        } catch (JSONException e) {
            Log.d(TAG, "json error msg:" + e.getMessage());
            return null;
        }
    }
}