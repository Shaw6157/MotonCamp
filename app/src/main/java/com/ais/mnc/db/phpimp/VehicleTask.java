package com.ais.mnc.db.phpimp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.VehicleBean;
import com.ais.mnc.db.bean.VehicleBean;
import com.ais.mnc.db.constant.TableConstant;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.VehicleListAdapter;

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
import java.util.List;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 29/11/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class VehicleTask extends AsyncTask<VehicleBean, Void, String> {
    private static final String TAG = "VehicleTask >>> ";

    private String mAction;
    private Context mContext;
    private DialogInterface mDialog;

    ArrayList<VehicleBean> VehicleList; //deal with the result list
    RecyclerView recycle_vlist;

    public VehicleTask(String action, Context context) {
        this.mAction = action;
        this.mContext = context;
    }

    @Override
    protected String doInBackground(VehicleBean... Vehicles) {
        StringBuilder sb = new StringBuilder("");
        try {
            URL url = new URL(ConfServer.SERVER_CONF + "function_vehicle.php");
            URLConnection lvConnection = url.openConnection();

//            $action_validation = ['createVehicle', 'findall', 'findbytype', 'updateVehicle', 'deleteVehicle'];
//            $vname, $vtype, $transm, $vyear, $vengin, $vprice, $vimage, $vinfo, $model
            String param = MncUtilities.encodeUTF8("action") + "="
                    + MncUtilities.encodeUTF8(mAction);

            if (!"findall".equals(mAction)) {
                param = param + "&"
                        + MncUtilities.encodeUTF8("vname") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getVname()) + "&"
                        + MncUtilities.encodeUTF8("vtype") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getType()) + "&"
                        + MncUtilities.encodeUTF8("transm") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getTransmission()) + "&"
                        + MncUtilities.encodeUTF8("vyear") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getYear()) + "&"
                        + MncUtilities.encodeUTF8("vengin") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getEngin()) + "&"
                        + MncUtilities.encodeUTF8("vprice") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getPrice()) + "&"
                        + MncUtilities.encodeUTF8("vimage") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getImage()) + "&"
                        + MncUtilities.encodeUTF8("vinfo") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getInfo()) + "&"
                        + MncUtilities.encodeUTF8("model") + "="
                        + MncUtilities.encodeUTF8(Vehicles[0].getModel());
            }

            Log.d(TAG, "92 param: " + param);
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
            Log.d(TAG, "111 background error msg:" + e.getMessage());
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
            case "findbytype":
                VehicleList = changeJsonToList(php_echo_str);

                if (VehicleList != null && VehicleList.size() > 0) {
                    //set adapter and list
                    recycle_vlist = ((Activity) mContext).findViewById(R.id.recycle_vlist);
                    recycle_vlist.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
                    recycle_vlist.setHasFixedSize(true);
                    recycle_vlist.setAdapter(new VehicleListAdapter(mContext, VehicleList));
                    Log.d(TAG, "  oooo adapter ......");

                } else {
                    MncUtilities.toastMessage(mContext, "No Vehicle in this category!");
                }

                break;
            case "createVehicle":
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
                    Log.d(TAG, "159 post-signup error msg:" + e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    private ArrayList<VehicleBean> changeJsonToList(String str_json) {
        try {
            ArrayList<VehicleBean> list = new ArrayList<VehicleBean>();
            JSONArray array = new JSONArray(str_json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Log.d(TAG, obj.toString());

                VehicleBean eachBean = new VehicleBean();
                eachBean.setVid(obj.optInt(TableConstant.VEHICLE_COL1_VID));
                eachBean.setVname(obj.optString(TableConstant.VEHICLE_COL2_VNAME));
                eachBean.setType(obj.optString(TableConstant.VEHICLE_COL3_TYPE));
                eachBean.setTransmission(obj.optString(TableConstant.VEHICLE_COL4_TRANSMISSION));
                eachBean.setYear(obj.optString(TableConstant.VEHICLE_COL5_YEAR));

                eachBean.setEngin(obj.optString(TableConstant.VEHICLE_COL6_ENGIN));
                eachBean.setPrice(obj.optInt(TableConstant.VEHICLE_COL7_PRICE));
                eachBean.setImage(obj.optString(TableConstant.VEHICLE_COL8_IMAGE));
                eachBean.setInfo(obj.optString(TableConstant.VEHICLE_COL9_INFO));
                eachBean.setModel(obj.optString(TableConstant.VEHICLE_COL10_MODEL));

                list.add(eachBean);
            }
            return list;
        } catch (JSONException e) {
            Log.d(TAG, "192 postexe error msg:" + e.getMessage());
            return null;
        }
    }
}
