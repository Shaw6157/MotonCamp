package com.ais.mnc.db.daoimp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ais.mnc.db.MncDBHelper;
import com.ais.mnc.db.bean.UserBean;
import com.ais.mnc.db.dao.UserDao;

import java.util.ArrayList;

import static com.ais.mnc.db.constant.TableConstant.*;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 20/10/2018
 * @author Shaw
 * @version 1.0
 */
public class UserDaoImp implements UserDao {
    private static final String TAG = "UserDAO >>> ";

    private MncDBHelper mDBHelper;
    public UserDaoImp(Context context) {
        mDBHelper = new MncDBHelper(context);
    }

    @Override
    public boolean createUser(UserBean p_user) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL2_UNAME, p_user.getUname());
        contentValues.put(USER_COL3_EMAIL, p_user.getEmail());
        contentValues.put(USER_COL4_PWD, p_user.getPassword());
        contentValues.put(USER_COL5_TYPE, "user");
        long result = db.insert(USER_TABLE_NAME, null, contentValues);

        //log
        Log.d(TAG, ">>> insert:  " + p_user.getUname() + " to " + USER_TABLE_NAME);

        //close and return
        db.close();
        return result != -1;
    }

    @Override
    public String getPassword(String p_uname) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        String str_pwd = "x";
        Cursor cursor = db.rawQuery(
                "SELECT " + USER_COL4_PWD
                        + " FROM " + USER_TABLE_NAME
                        + " WHERE " + USER_COL2_UNAME
                        + " = '" + p_uname + "'", null);
        if (cursor.moveToFirst()) {
            str_pwd = cursor.getString(0);
        }

        //log
        Log.d(TAG, ">>> getPassword: " + p_uname + " in " + USER_TABLE_NAME);

        //close and return
        cursor.close();
        db.close();
        return str_pwd;
    }

    @Override
    public UserBean findByName(String p_uname) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        UserBean lvUserBean = null;
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME
                + " WHERE " + USER_COL2_UNAME + " = '" + p_uname + "'";
        Log.d(TAG, "QUERY by name: " +selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            lvUserBean = new UserBean(
                    c.getInt(c.getColumnIndex(USER_COL1_UID)),
                    c.getString(c.getColumnIndex(USER_COL2_UNAME)),
                    c.getString(c.getColumnIndex(USER_COL3_EMAIL)),
                    c.getString(c.getColumnIndex(USER_COL4_PWD))
            );
        }

        //log
        Log.d(TAG, ">>> getPassword: " + p_uname + " in " + USER_TABLE_NAME);

        //close and return
        c.close();
        db.close();
        return lvUserBean;
    }

    @Override
    public ArrayList<UserBean> findAll() {
        return null;
    }

    @Override
    public boolean updateUser(UserBean p_user) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_COL2_UNAME, p_user.getUname());
        values.put(USER_COL3_EMAIL, p_user.getEmail());
        values.put(USER_COL4_PWD, p_user.getPassword());

        // updating row
        long result = db.update(USER_TABLE_NAME, values,
                USER_COL2_UNAME + " = ?",
                new String[] { String.valueOf(p_user.getUname()) });

        //log
        Log.d(TAG, ">>> update: " + p_user.getUname() + " in " + USER_TABLE_NAME);

        //close and return
        db.close();
        return result != -1;
    }

    @Override
    public boolean deleteUser(int id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int result = db.delete(USER_TABLE_NAME,
                USER_COL1_UID + " = ?",
                new String[] { String.valueOf(id) });

        Log.d(TAG, ">>> delete: " + id + " in " + USER_TABLE_NAME);
        db.close();
        return result != -1;
    }

    @Override
    public boolean checkExist(String p_uname, String p_email) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        boolean flag = false;

        //log
        Log.d(TAG, " checkExist: " + p_uname + " in " + USER_TABLE_NAME);

        //check user name
        Cursor cursor = db.rawQuery(
                "SELECT " + USER_COL4_PWD
                        + " FROM " + USER_TABLE_NAME
                        + " WHERE " + USER_COL2_UNAME
                        + " = '" + p_uname + "'", null);
        if (cursor.moveToFirst()) {
            flag = true;
        }
        cursor.close();

        //check user email, when sign up
        if (!flag && !"".equals(p_email)) {
            Cursor cursor2 = db.rawQuery(
                    "SELECT " + USER_COL4_PWD
                            + " FROM " + USER_TABLE_NAME
                            + " WHERE " + USER_COL3_EMAIL
                            + " = '" + p_email + "'", null);
            if (cursor2.moveToFirst()) {
                flag = true;
            }
            cursor2.close();
        }

        //close and return
        db.close();
        return flag;    //login for true, and sign up for false
    }

    public boolean removeUser(int id) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long result = db.delete(USER_TABLE_NAME,
                USER_COL1_UID + " = ?",
                new String[] { String.valueOf(id) });

        Log.d(TAG, ">>> remove: " + id + " in " + USER_TABLE_NAME);
        db.close();
        return result != -1;
    }
}
