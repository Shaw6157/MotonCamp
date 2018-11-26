package com.ais.mnc.db;

import android.database.Cursor;
import android.util.ArrayMap;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 22/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class ColumnIndexCache {
    private ArrayMap<String, Integer> mMap = new ArrayMap<>();

    public int getColumnIndex(Cursor cursor, String columnName) {
        if (!mMap.containsKey(columnName)) {
            mMap.put(columnName, cursor.getColumnIndex(columnName));
        }
        return mMap.get(columnName);
    }

    public void clear() {
        mMap.clear();
    }
}
