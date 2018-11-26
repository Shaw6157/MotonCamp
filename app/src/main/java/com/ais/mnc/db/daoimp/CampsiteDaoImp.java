package com.ais.mnc.db.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ais.mnc.db.ColumnIndexCache;
import com.ais.mnc.db.MncDBHelper;
import com.ais.mnc.db.bean.CampBean;
import com.ais.mnc.db.dao.CampsiteDao;

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
public class CampsiteDaoImp implements CampsiteDao {
    private static final String TAG = "CampsiteDaoImp >>> ";

    private MncDBHelper mDBHelper;
    public CampsiteDaoImp(Context context) {
        mDBHelper = new MncDBHelper(context);
    }

    @Override
    public boolean createCampsite(CampBean p_campsite) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues campValues = new ContentValues();
        campValues.put(CAMP_COL2_CNAME,   p_campsite.getCname());
        campValues.put(CAMP_COL3_ADDRESS, p_campsite.getAddress());
        campValues.put(CAMP_COL4_INFO,    p_campsite.getInfo());
        campValues.put(CAMP_COL5_URL,     p_campsite.getUrl());
        campValues.put(CAMP_COL6_IMAGE,   p_campsite.getImage());
        campValues.put(CAMP_COL7_FEATURES,   p_campsite.getFeatures());
        campValues.put(CAMP_COL8_lAT,   p_campsite.getLAT());
        campValues.put(CAMP_COL9_LNG,   p_campsite.getLNG());
        campValues.put(CAMP_COL10_PHONE,   p_campsite.getLNG());
        long result = db.insert(CAMP_TABLE_NAME, null, campValues);

        //log
        Log.d(TAG, ">>> insert:  " + p_campsite.getCname() + " to " + CAMP_TABLE_NAME);

        //close and return
        db.close();
        return result != -1;
    }

    @Override
    public boolean updateCampsite(CampBean p_campsite) {
        return false;
    }

    @Override
    public boolean deleteCampsite(int cid) {
        return false;
    }

    @Override
    public CampBean findById(int cid) {
        return null;
    }

    @Override
    public ArrayList<CampBean> findAll() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + CAMP_TABLE_NAME;
        Log.d(TAG, "QUERY: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            ArrayList<CampBean> campsiteList = new ArrayList<CampBean>(c.getCount());
            ColumnIndexCache cache = new ColumnIndexCache();

            // looping through all rows and adding to list
            do {
                campsiteList.add(
                        new CampBean(
                                c.getInt(c.getColumnIndex(CAMP_COL1_CID)),
                                c.getString(c.getColumnIndex(CAMP_COL2_CNAME)),
                                c.getString(c.getColumnIndex(CAMP_COL3_ADDRESS)),
                                c.getString(c.getColumnIndex(CAMP_COL4_INFO)),
                                c.getString(c.getColumnIndex(CAMP_COL5_URL)),
                                c.getString(c.getColumnIndex(CAMP_COL6_IMAGE)),
                                c.getString(c.getColumnIndex(CAMP_COL7_FEATURES)),
                                c.getDouble(c.getColumnIndex(CAMP_COL8_lAT)),
                                c.getDouble(c.getColumnIndex(CAMP_COL9_LNG)),
                                c.getString(c.getColumnIndex(CAMP_COL10_PHONE))
                        ));
            } while (c.moveToNext());
            cache.clear();
            c.close();
            db.close();
            return campsiteList;
        }
        c.close();
        db.close();
        return null;
    }
}
