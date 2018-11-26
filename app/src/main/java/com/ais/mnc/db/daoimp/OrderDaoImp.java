package com.ais.mnc.db.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ais.mnc.db.ColumnIndexCache;
import com.ais.mnc.db.MncDBHelper;
import com.ais.mnc.db.bean.OrderBean;
import com.ais.mnc.db.dao.OrderDao;

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
public class OrderDaoImp implements OrderDao {
    private static final String TAG = "OrderDaoImp >>> ";

    private MncDBHelper mDBHelper;
    public OrderDaoImp(Context context) {
        mDBHelper = new MncDBHelper(context);
    }

    @Override
    public boolean createOrder(OrderBean order) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_COL2_VID, order.getOid());
        contentValues.put(ORDER_COL3_UID, order.getUid());
        contentValues.put(ORDER_COL4_DATABG, order.getDatebg());
        contentValues.put(ORDER_COL5_DATAED, order.getDateed());
        contentValues.put(ORDER_COL6_AMOUNT, order.getAmount());
        contentValues.put(ORDER_COL7_DATA, order.getOdate());
        contentValues.put(ORDER_COL8_STATE, "10");
        contentValues.put(ORDER_COL9_CONTACT, order.getContactName());
        contentValues.put(ORDER_COL10_PHONE, order.getContactPhone());
        long result = db.insert(ORDER_TABLE_NAME, null, contentValues);

        //log
        Log.d(TAG, ">>> insert:  " + order.getUid() + " to " + ORDER_TABLE_NAME);

        //close and return
        db.close();
        return result != -1;
    }

    @Override
    public boolean updateOrder(OrderBean order) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDER_COL2_VID, order.getVid());
        contentValues.put(ORDER_COL3_UID, order.getUid());
        contentValues.put(ORDER_COL4_DATABG, order.getDatebg());
        contentValues.put(ORDER_COL5_DATAED, order.getDateed());
        contentValues.put(ORDER_COL6_AMOUNT, order.getAmount());
        contentValues.put(ORDER_COL7_DATA, order.getOdate());
        contentValues.put(ORDER_COL8_STATE, "10");
        contentValues.put(ORDER_COL9_CONTACT, order.getContactName());
        contentValues.put(ORDER_COL10_PHONE, order.getContactPhone());

        long result = db.update(ORDER_TABLE_NAME, contentValues, ORDER_COL1_OID + " = ?",
                new String[]{String.valueOf(order.getOid())});

        //log
        Log.d(TAG, ">>> UPDATE:  " + order.getUid() + " to " + ORDER_TABLE_NAME);

        //close and return
        db.close();
        return result != -1;
    }

    @Override
    public boolean deleteOrder(int oid) {
        return false;
    }

    @Override
    public OrderBean findById(int oid) {
        return null;
    }

    @Override
    public ArrayList<OrderBean> findByUID(int uid) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ORDER_TABLE_NAME
                + " WHERE " + ORDER_COL3_UID + " = " + uid;

        //TODO equal the user type
        if (1 == uid){
            selectQuery = "SELECT * FROM " + ORDER_TABLE_NAME;
        }
        Log.d(TAG, "QUERY: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return fillList(c);
        }
        return null;
    }

    @Override
    public ArrayList<OrderBean> findByVID(int vid) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ORDER_TABLE_NAME
                + " WHERE " + ORDER_COL2_VID + " = " + vid;
        Log.d(TAG, "QUERY: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return fillList(c);
        }
        return null;
    }

    @Override
    public ArrayList<OrderBean> findAll() {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ORDER_TABLE_NAME;
        Log.d(TAG, "QUERY: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            return fillList(c);
        }
        return null;
    }

    private ArrayList<OrderBean> fillList(Cursor c) {
        ArrayList<OrderBean> orderList = new ArrayList<OrderBean>(c.getCount());
        ColumnIndexCache cache = new ColumnIndexCache();

        do {
            orderList.add(
                    new OrderBean(
                            c.getInt   (cache.getColumnIndex(c, ORDER_COL1_OID)),
                            c.getInt   (cache.getColumnIndex(c, ORDER_COL2_VID)),
                            c.getInt   (cache.getColumnIndex(c, ORDER_COL3_UID)),
                            c.getString(cache.getColumnIndex(c, ORDER_COL4_DATABG)),
                            c.getString(cache.getColumnIndex(c, ORDER_COL5_DATAED)),
                            c.getInt   (cache.getColumnIndex(c, ORDER_COL6_AMOUNT)),
                            c.getString(cache.getColumnIndex(c, ORDER_COL7_DATA)),
                            c.getString(cache.getColumnIndex(c, ORDER_COL8_STATE)),
                            c.getString(cache.getColumnIndex(c, ORDER_COL9_CONTACT)),
                            c.getString(cache.getColumnIndex(c, ORDER_COL10_PHONE))
                    ));
        } while (c.moveToNext());
        cache.clear();
        return orderList;
    }
}
