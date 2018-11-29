package com.ais.mnc.db.phpimp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.ais.mnc.R;
import com.ais.mnc.db.bean.OrderBean;
import com.ais.mnc.db.constant.TableConstant;
import com.ais.mnc.util.MncUtilities;
import com.ais.mnc.view.adapter.OrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 28/11/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class OrderTask extends AsyncTask<OrderBean, Void, String> {
    private static final String TAG = "OrderTask >>> ";

    private String mAction;
    private Context mContext;
    private DialogInterface mDialog;

    OrderBean[] mOrderBeans;        //get params from activity
    ArrayList<OrderBean> orderList; //deal with the result list
    RecyclerView recycle_olist;

    public OrderTask(String action) {
        this.mAction = action;
    }

    public OrderTask(String action, Context context) {
        this.mAction = action;
        this.mContext = context;
    }

    public OrderTask(String action, Context context, DialogInterface dialog) {
        this.mAction = action;
        this.mContext = context;
        this.mDialog = dialog;
    }

    @Override
    protected String doInBackground(OrderBean... orders) {
        StringBuilder sb = new StringBuilder("");
        mOrderBeans = orders;
        try {
            URL url = new URL(ConfServer.SERVER_CONF + "function_order.php");
            URLConnection lvConnection = url.openConnection();

//          $action_validation = ['createorder', 'findall', 'updateorder', 'deleteorder'];
            String param = MncUtilities.encodeUTF8("action") + "="
                    + MncUtilities.encodeUTF8(mAction);

            if (!"findall".equals(mAction)) {
                param = param + "&"
                        + MncUtilities.encodeUTF8("vid") + "="
                        + MncUtilities.encodeUTF8(orders[0].getVid()) + "&"
                        + MncUtilities.encodeUTF8("uid") + "="
                        + MncUtilities.encodeUTF8(orders[0].getUid()) + "&"
                        + MncUtilities.encodeUTF8("datebg") + "="
                        + MncUtilities.encodeUTF8(orders[0].getDatebg()) + "&"
                        + MncUtilities.encodeUTF8("dateed") + "="
                        + MncUtilities.encodeUTF8(orders[0].getDateed()) + "&"
                        + MncUtilities.encodeUTF8("amount") + "="
                        + MncUtilities.encodeUTF8(orders[0].getAmount()) + "&"
                        + MncUtilities.encodeUTF8("odate") + "="
                        + MncUtilities.encodeUTF8(orders[0].getOdate()) + "&"
                        + MncUtilities.encodeUTF8("ostate") + "="
                        + MncUtilities.encodeUTF8(orders[0].getOstate()) + "&"
                        + MncUtilities.encodeUTF8("contactName") + "="
                        + MncUtilities.encodeUTF8(orders[0].getContactName()) + "&"
                        + MncUtilities.encodeUTF8("contactPhone") + "="
                        + MncUtilities.encodeUTF8(orders[0].getContactPhone());
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

        if (php_echo_str.isEmpty()) {
            MncUtilities.toastMessage(mContext, "No data fetched!");
            return;
        }

        switch (mAction) {
            case "findall":
                orderList = new ArrayList<OrderBean>();
                try {
                    JSONArray array = new JSONArray(php_echo_str);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Log.d(TAG, obj.toString());

                        OrderBean eachBean = new OrderBean();
                        eachBean.setOid(obj.optInt(TableConstant.ORDER_COL1_OID));
                        eachBean.setUid(obj.optInt(TableConstant.ORDER_COL2_VID));
                        eachBean.setVid(obj.optInt(TableConstant.ORDER_COL3_UID));

                        eachBean.setDatebg(obj.optString(TableConstant.ORDER_COL4_DATABG));
                        eachBean.setDateed(obj.optString(TableConstant.ORDER_COL5_DATAED));

                        eachBean.setAmount(obj.optInt(TableConstant.ORDER_COL6_AMOUNT));

                        eachBean.setOdate(obj.optString(TableConstant.ORDER_COL7_DATA));
                        eachBean.setOstate(obj.optString(TableConstant.ORDER_COL8_STATE));
                        eachBean.setContactName(obj.optString(TableConstant.ORDER_COL9_CONTACT));
                        eachBean.setContactPhone(obj.optString(TableConstant.ORDER_COL10_PHONE));
                        orderList.add(eachBean);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "postexe error msg:" + e.getMessage());
                }

                if (orderList != null && orderList.size() > 0) {
                    //set adapter and list
                    recycle_olist = ((Activity) mContext).findViewById(R.id.recycle_olist);
                    recycle_olist.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
                    recycle_olist.setHasFixedSize(true);
                    recycle_olist.setAdapter(new OrderAdapter(mContext, orderList));
                    Log.d(TAG, "  oooo adapter ......");

                    BottomNavigationView odlt_btm_nav = ((Activity) mContext).findViewById(R.id.odlt_btm_nav);
                    odlt_btm_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.odlt_nav_booking) {
//                                recycle_olist.setAdapter(new OrderAdapter(this, returnList));
                                filterList(orderList, "10");
                            } else if (menuItem.getItemId() == R.id.odlt_nav_progress) {
                                filterList(orderList, "20");
                            } else if (menuItem.getItemId() == R.id.odlt_nav_cancel) {
                                filterList(orderList, "90");
                            } else {
                                filterList(orderList, "30");
                            }
                            return true;
                        }
                    });
                } else {
                    MncUtilities.toastMessage(mContext, "No order in this user");
                }

                break;
            case "createorder":
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
                    Log.d(TAG, "post-signup error msg:" + e.getMessage());
                }
                break;
            default:
                break;
        }

    }

    private void filterList(List<OrderBean> olist, String status) {
        if ("0".equals(status)) {
            return;
        } else {
//            Log.d(TAG, " filter clicked ......");
            ArrayList<OrderBean> returnList = new ArrayList<>();
            if (olist != null) {
                for (OrderBean bean : olist) {
                    if (status.equals(bean.getOstate())) {
                        returnList.add(bean);
                    }
                }
            }
            recycle_olist.setAdapter(new OrderAdapter(mContext, returnList));
        }
    }
}
