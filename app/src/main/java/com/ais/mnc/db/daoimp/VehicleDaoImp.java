package com.ais.mnc.db.daoimp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ais.mnc.db.ColumnIndexCache;
import com.ais.mnc.db.MncDBHelper;
import com.ais.mnc.db.bean.PhotoBean;
import com.ais.mnc.db.bean.VehicleBean;
import com.ais.mnc.db.dao.VehicleDao;

import java.util.ArrayList;

import static com.ais.mnc.db.constant.TableConstant.*;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 21/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class VehicleDaoImp implements VehicleDao {
    private static final String TAG = "VehicleDaoImp >>> ";

    private MncDBHelper mDBHelper;
    public VehicleDaoImp(Context context) {
        mDBHelper = new MncDBHelper(context);
    }

    @Override
    public boolean createVehicle(VehicleBean p_vehicle) {
        return false;
    }

    @Override
    public boolean updateVehicle(VehicleBean p_vehicle) {
        return false;
    }

    @Override
    public boolean deleteVehicle(int pid) {
        return false;
    }

    @Override
    public VehicleBean findById(int pid) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        VehicleBean vehicle = null;
        String selectQuery = "SELECT * FROM " + VEHICLE_TABLE_NAME
                + " WHERE " + VEHICLE_COL1_VID + " = " + pid;
        Log.d(TAG, "QUERY by id: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            vehicle = new VehicleBean(
                    c.getInt   (c.getColumnIndex(VEHICLE_COL1_VID)),
                    c.getString(c.getColumnIndex(VEHICLE_COL2_VNAME)),
                    c.getString(c.getColumnIndex(VEHICLE_COL3_TYPE)),
                    c.getString(c.getColumnIndex(VEHICLE_COL4_TRANSMISSION)),
                    c.getString(c.getColumnIndex(VEHICLE_COL5_YEAR)),
                    c.getString(c.getColumnIndex(VEHICLE_COL6_ENGIN)),
                    c.getInt   (c.getColumnIndex(VEHICLE_COL7_PRICE)),
                    c.getString(c.getColumnIndex(VEHICLE_COL8_IMAGE)),
                    c.getString(c.getColumnIndex(VEHICLE_COL9_INFO)),
                    c.getString(c.getColumnIndex(VEHICLE_COL10_MODEL)),
                    null
            );
        }
        c.close();
        db.close();
        return vehicle;
    }

    @Override
    public ArrayList<VehicleBean> findByType(String type) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + VEHICLE_TABLE_NAME
                + " WHERE " + VEHICLE_COL3_TYPE + " = '" + type + "'";

        if ("9".equals(type)){
            selectQuery = "SELECT * FROM " + VEHICLE_TABLE_NAME;
        }
        Log.d(TAG, "QUERY: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return fillList(c, db);
        }
        c.close();
        db.close();
        return null;
    }

    @Override
    public ArrayList<VehicleBean> findAll() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + VEHICLE_TABLE_NAME;
        Log.d(TAG, "QUERY: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return fillList(c, db);
        }
        c.close();
        db.close();
        return null;
    }

    //fill list method
    private ArrayList<VehicleBean> fillList(Cursor c, SQLiteDatabase db) {
        ArrayList<VehicleBean> vehicleList = new ArrayList<VehicleBean>(c.getCount());
        //set column cache
        ColumnIndexCache cache = new ColumnIndexCache();

        // looping through all rows and adding to list
        do {
            vehicleList.add(
                    new VehicleBean(
                            c.getInt   (cache.getColumnIndex(c, VEHICLE_COL1_VID)),
                            c.getString(cache.getColumnIndex(c, VEHICLE_COL2_VNAME)),
                            c.getString(cache.getColumnIndex(c, VEHICLE_COL3_TYPE)),
                            c.getString(cache.getColumnIndex(c, VEHICLE_COL4_TRANSMISSION)),
                            c.getString(cache.getColumnIndex(c, VEHICLE_COL5_YEAR)),
                            c.getString(cache.getColumnIndex(c, VEHICLE_COL6_ENGIN)),
                            c.getInt   (cache.getColumnIndex(c, VEHICLE_COL7_PRICE)),
                            c.getString(cache.getColumnIndex(c, VEHICLE_COL8_IMAGE)),
                            c.getString(cache.getColumnIndex(c, VEHICLE_COL9_INFO)),
                            c.getString(cache.getColumnIndex(c, VEHICLE_COL10_MODEL)),
                            null    //TODO
                    ));
        } while (c.moveToNext());
        cache.clear();

        c.close();
        db.close();
        return vehicleList;
    }

    @Override
    public ArrayList<String> getAllTypes() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT DISTINCT (" + VEHICLE_COL3_TYPE + ") FROM " + VEHICLE_TABLE_NAME;
        Log.d(TAG, "QUERY: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            ArrayList<String> vTypeList = new ArrayList<String>(c.getCount());
            while (c.moveToNext()) {
                vTypeList.add(c.getString(0));
            }
            c.close();
            db.close();
            return vTypeList;
        }
        c.close();
        db.close();
        return null;
    }
}
