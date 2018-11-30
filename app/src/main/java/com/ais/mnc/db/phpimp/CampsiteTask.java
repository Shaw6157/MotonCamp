package com.ais.mnc.db.phpimp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.constant.TableConstant;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.CampsiteListAdapter;

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
 * Created on 29/11/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class CampsiteTask extends AsyncTask<CampBean, Void, String> {
    private static final String TAG = "CampsiteTask >>> ";

    private String mAction;
    private Context mContext;
    private DialogInterface mDialog;

    ArrayList<CampBean> CampsiteList; //deal with the result list
    RecyclerView recycle_clist;

    public CampsiteTask(String action, Context context) {
        this.mAction = action;
        this.mContext = context;
    }

    @Override
    protected String doInBackground(CampBean... campsites) {
        StringBuilder sb = new StringBuilder("");
        try {
            URL url = new URL(ConfServer.SERVER_CONF + "function_campsite.php");
            URLConnection lvConnection = url.openConnection();

            String param = MncUtilities.encodeUTF8("action") + "="
                    + MncUtilities.encodeUTF8(mAction);

            if (!"findall".equals(mAction)) {
//                $cname, $address, $info, $url, $image, $features, $lat, $lng, $cphone
                param = param + "&"
                        + MncUtilities.encodeUTF8("cname") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getCname()) + "&"
                        + MncUtilities.encodeUTF8("address") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getAddress()) + "&"
                        + MncUtilities.encodeUTF8("info") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getInfo()) + "&"
                        + MncUtilities.encodeUTF8("url") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getUrl()) + "&"
                        + MncUtilities.encodeUTF8("image") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getImage()) + "&"
                        + MncUtilities.encodeUTF8("features") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getFeatures()) + "&"
                        + MncUtilities.encodeUTF8("lat") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getLAT()) + "&"
                        + MncUtilities.encodeUTF8("lng") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getLNG()) + "&"
                        + MncUtilities.encodeUTF8("cphone") + "="
                        + MncUtilities.encodeUTF8(campsites[0].getPhone());
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
                Log.d(TAG, "echo:" + sb.toString());
            } else {
                Log.d(TAG, "echo:  nothing return to stringbuilder");
            }
        } catch (IOException e) {
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

        if (php_echo_str.isEmpty()) {
            MncUtilities.toastMessage(mContext, "No data fetched!");
            return;
        }

        switch (mAction) {
            case "findall":
                //fetch data
                CampsiteList = changeJsonToList(php_echo_str);
                //set into recyclerview
                if (CampsiteList != null && CampsiteList.size() > 0) {
                    
                    
                    //set context campsite list
                    recycle_clist = ((Activity) mContext).findViewById(R.id.clst_lyt_recycle);

                    recycle_clist.setLayoutManager(new GridLayoutManager(mContext, 2));
                    if (checkScreenSize() > 2559) {
                        recycle_clist.setLayoutManager(new GridLayoutManager(mContext, 4));
                    }
                    recycle_clist.setHasFixedSize(true);
                    recycle_clist.setAdapter(
                            new CampsiteListAdapter(mContext, CampsiteList));
                } else {
                    MncUtilities.toastMessage(mContext, "No Campsite data");
                }
                break;
            case "createcampsite":
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
                    Log.d(TAG, "post-camp error msg:" + e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    private int checkScreenSize() {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        Log.d(TAG, "checkScreenSize: " + metric.widthPixels);
        return metric.widthPixels;
//        int width = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

    }

    private ArrayList<CampBean> changeJsonToList(String str_json) {
        try {
            ArrayList<CampBean> list = new ArrayList<CampBean> ();
            JSONArray array = new JSONArray(str_json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.d(TAG, obj.toString().substring(0, 50));

                CampBean eachBean = new CampBean();
                eachBean.setCid(obj.optInt(TableConstant.CAMP_COL1_CID));
                eachBean.setCname(obj.optString(TableConstant.CAMP_COL2_CNAME));
                eachBean.setAddress(obj.optString(TableConstant.CAMP_COL3_ADDRESS));
                eachBean.setInfo(obj.optString(TableConstant.CAMP_COL4_INFO));
                eachBean.setUrl(obj.optString(TableConstant.CAMP_COL5_URL));

                eachBean.setImage(obj.optString(TableConstant.CAMP_COL6_IMAGE));
                eachBean.setFeatures(obj.optString(TableConstant.CAMP_COL7_FEATURES));
                eachBean.setLAT(obj.optDouble(TableConstant.CAMP_COL8_lAT));
                eachBean.setLNG(obj.optDouble(TableConstant.CAMP_COL9_LNG));
                eachBean.setPhone(obj.optString(TableConstant.CAMP_COL10_PHONE));

                list.add(eachBean);
            }
            return list;
        } catch (JSONException e) {
            Log.d(TAG, "json error msg:" + e.getMessage());
            return null;
        }
    }
}
