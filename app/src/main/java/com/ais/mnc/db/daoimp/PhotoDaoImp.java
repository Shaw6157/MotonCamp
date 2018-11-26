package com.ais.mnc.db.daoimp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ais.mnc.db.ColumnIndexCache;
import com.ais.mnc.db.MncDBHelper;
import com.ais.mnc.db.bean.PhotoBean;
import com.ais.mnc.db.dao.PhotoDao;

import java.util.ArrayList;

import static com.ais.mnc.db.constant.TableConstant.*;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 25/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class PhotoDaoImp implements PhotoDao {
    private static final String TAG = "PhotoDaoImp >>> ";

    private MncDBHelper mDBHelper;
    public PhotoDaoImp(Context context) {
        mDBHelper = new MncDBHelper(context);
    }

    @Override
    public boolean createPhoto(PhotoBean photo) {
        return false;
    }

    @Override
    public boolean updatePhoto(PhotoBean photo) {
        return false;
    }

    @Override
    public boolean deletePhoto(int pid) {
        return false;
    }

    @Override
    public PhotoBean findById(int pid) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        PhotoBean returnPhoto = null;

        String selectQuery = "SELECT * FROM " + PHOTO_TABLE_NAME
                + " WHERE " + PHOTO_COL7_DEL + " <> " + 1
                + " AND " + PHOTO_COL1_PID + " = " + pid;

        Log.d(TAG, "QUERY: " + selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            returnPhoto = new PhotoBean(
                    c.getInt   (c.getColumnIndex(PHOTO_COL1_PID)),
                    c.getInt   (c.getColumnIndex(PHOTO_COL2_CID)),
                    c.getInt   (c.getColumnIndex(PHOTO_COL3_UID)),
                    c.getString(c.getColumnIndex(PHOTO_COL4_DATE)),
                    c.getString(c.getColumnIndex(PHOTO_COL5_PATH)),
                    c.getString(c.getColumnIndex(PHOTO_COL6_DESC)),
                    false
            );
        }
        c.close();
        db.close();
        return returnPhoto;
    }

    @Override
    public ArrayList<PhotoBean> findByCID(int cid) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + PHOTO_TABLE_NAME
                + " WHERE " + PHOTO_COL7_DEL + " <> " + 1
                + " AND " + PHOTO_COL2_CID + " = " + cid;

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
    public ArrayList<PhotoBean> findByUID(int uid) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + PHOTO_TABLE_NAME
                + " WHERE " + PHOTO_COL7_DEL + " <> " + 1
                + " AND " + PHOTO_COL3_UID + " = " + uid;

        Log.d(TAG, "QUERY: " +selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return fillList(c, db);
        }

        //close and return
        c.close();
        db.close();
        return null;
    }

    @Override
    public ArrayList<PhotoBean> findAll() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + PHOTO_TABLE_NAME
                + " WHERE " + PHOTO_COL7_DEL + " <> " + 1;

        Log.d(TAG, "QUERY: " +selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return fillList(c, db);
        }

        //close and return
        c.close();
        db.close();
        return null;
    }

    private ArrayList<PhotoBean> fillList(Cursor c, SQLiteDatabase db) {
        ArrayList<PhotoBean> photoList = new ArrayList<PhotoBean>(c.getCount());
        //set column cache
        ColumnIndexCache cache = new ColumnIndexCache();
        // looping through all rows and adding to list
        do {
            photoList.add(
                    new PhotoBean(
                            c.getInt   (cache.getColumnIndex(c, PHOTO_COL1_PID)),
                            c.getInt   (cache.getColumnIndex(c, PHOTO_COL2_CID)),
                            c.getInt   (cache.getColumnIndex(c, PHOTO_COL3_UID)),
                            c.getString(cache.getColumnIndex(c, PHOTO_COL4_DATE)),
                            c.getString(cache.getColumnIndex(c, PHOTO_COL5_PATH)),
                            c.getString(cache.getColumnIndex(c, PHOTO_COL6_DESC)),
                            false
                    ));
        } while (c.moveToNext());
        cache.clear();

        c.close();
        db.close();
        return photoList;
    }

}
