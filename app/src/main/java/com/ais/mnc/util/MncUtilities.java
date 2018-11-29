package com.ais.mnc.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.bean.PhotoBean;
import com.ais.mnc.db.bean.UserBean;
import com.ais.mnc.db.bean.VehicleBean;
import com.ais.mnc.db.phpimp.ConfServer;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 18/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class MncUtilities {
    private static final String TAG = "MncUtilities >>> ";
//    private static final String SERVER_ENC = "UTF-8";

    public static UserBean currentUser = null;
    public static CampBean currentCpsite = null;
    public static VehicleBean currentVehicle = null;
    public static String currentVehicleType = null;
    public static List<PhotoBean> currentPhotoList = null;
    public static List<CampBean> currentCampList = null;

    public static Class previousClass = null;

    public static void startNextActivity(Context context, Class c, boolean isFinish) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
        if (isFinish) {
            ((Activity) context).finish();
        }
    }

    public static void startNextActivity(Context context, Class c, String argu, boolean isFinish) {
        Intent intent = new Intent(context, c);
        if (argu != null && !"".equals(argu)) {
            intent.putExtra("para", argu);
        }
        context.startActivity(intent);
        if (isFinish) {
            ((Activity) context).finish();
        }
    }

    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());   //yyyy-MM-dd HH:mm:ss
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateString(long date) {
        Date d = new Date(date + 24 * 60 * 60 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        return format.format(d);
    }

    public static String getDateLongString(long date) {
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(d);
    }

    public static void showTitleDialog(Context context, final TextView textView) {
        final String[] titles = new String[]{"Mr.", "Mrs", "Dr", "Miss", "Ms"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setSingleChoiceItems(titles, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                textView.setText(titles[position]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void showDateDialog(Context context, final EditText editText) {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editText.setText(dayOfMonth + "-" + (month + 1) + year);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    public static void setMncImage(Context context, String str_img, ImageView vImg) {
        Picasso.with(context)
                .load(str_img)
                .into(vImg);
    }

    public static String getStatusTxt(String ostate) {
        if (ostate != null) {
            if ("10".equals(ostate)) {
                return "Booking";
            } else if ("20".equals(ostate)) {
                return "in progress";
            } else if ("30".equals(ostate)) {
                return "Completed";
            } else if ("90".equals(ostate)) {
                return "Canceled";
            } else {
                return "Unknown";
            }
        }
        return "";
    }

    public static boolean isEmptyTxt(String str) {
        return (str == null) || (str.length() < 1);
    }

    public static String encodeUTF8(String str) {
        try {
            return URLEncoder.encode(str, ConfServer.SERVER_ENC);
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "encoding error:" + e.getMessage());
        }
        return "er";
    }

    public static String encodeUTF8(int int_str) {
        try {
            return URLEncoder.encode(String.valueOf(int_str), ConfServer.SERVER_ENC);
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "encoding error:" + e.getMessage());
        }
        return "er";
    }


}
