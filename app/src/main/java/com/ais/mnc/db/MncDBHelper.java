package com.ais.mnc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.ais.mnc.db.constant.TableConstant.*;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 18/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class MncDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "MncDBHelper >>> ";

    public static final String DATABSE_NAME = "dbMNC";
    public static final int DATABASE_VERSION = 1;

    public MncDBHelper(Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_VEHICLE_TABLE);
        db.execSQL(CREATE_CAMP_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);
        db.execSQL(CREATE_PHOTO_TABLE);
        Log.d(TAG, "init............: ");

        //add default admin user account
        String query_user_init = "INSERT INTO "    + USER_TABLE_NAME
                + " SELECT 1 AS "               + USER_COL1_UID
                + ", 'admin' AS "               + USER_COL2_UNAME
                + ", 'shaw.yunz@gmail.com' AS " + USER_COL3_EMAIL
                + ", 'adminshaw' AS "           + USER_COL4_PWD
                + ", 'admin' AS "               + USER_COL5_TYPE
                + " UNION SELECT 2, 'aaa', 'aaa', 'aaa', 'user'"
                + " UNION SELECT 3, 'Jhon', 'jhon@j.com', 'jhon', 'user'"
                + " UNION SELECT 4, 'Cris', 'cris@c.com', 'cris', 'user'"
                + " UNION SELECT 5, 'test', 'test@t.com', 'test', 'user'"
                ;
        db.execSQL(query_user_init);

        String query_vehicle_init = "INSERT INTO "    + VEHICLE_TABLE_NAME
                + " SELECT 1 AS "               + VEHICLE_COL1_VID
                + ", 'Lucky Rookie' AS "               + VEHICLE_COL2_VNAME
                + ", '1' AS "               + VEHICLE_COL3_TYPE
                + ", 'Automatic' AS "           + VEHICLE_COL4_TRANSMISSION
                + ", '1997-1999' AS "               + VEHICLE_COL5_YEAR
                + ", '2.4L' AS "               + VEHICLE_COL6_ENGIN
                + ", 343 AS "               + VEHICLE_COL7_PRICE
                + ", 'https://tinyurl.com/mnc-vehicle0001' AS "               + VEHICLE_COL8_IMAGE
                + ", 'This vehicle is a well-loved campervan which is ideal for backpackers or the budget conscious who prefer saving money to driving the latest vehicle models.' AS "               + VEHICLE_COL9_INFO
                + ", 'Toyota Estima' AS "               + VEHICLE_COL10_MODEL
                + " UNION SELECT 2, 'Original Black Sheep', '1', 'Automatic', '2001', '3400', 449, 'https://www.gonewiththewynns.com/wp-content/uploads/2012/08/airstream_interstate_exterior1.jpg',"
                + " 'Some brief introduction about Black sheep', 'Toyota Grandvia'"
                + " UNION SELECT 3, 'Sleepervan', '2', 'Automatic', '1996', '2000cc', 451, 'https://www.gonewiththewynns.com/wp-content/uploads/2012/08/airstream_interstate_exterior1.jpg',"
                + " 'this is one of our best seller! check it out now!', 'Toyota'"
                ;

        Log.d(TAG, "init  vehicle ............: " + query_vehicle_init);
        db.execSQL(query_vehicle_init);

        String query_order_init = "INSERT INTO "    + ORDER_TABLE_NAME
                + " SELECT 101 AS "               + ORDER_COL1_OID
                + ", 3 AS "                   + ORDER_COL2_VID
                + ", 2 AS "                   + ORDER_COL3_UID
                + ", '12/09/2018' AS "           + ORDER_COL4_DATABG
                + ", '22/09/2018' AS "               + ORDER_COL5_DATAED
                + ", 3210 AS "               + ORDER_COL6_AMOUNT
                + ", '20/09/2018' AS "               + ORDER_COL7_DATA
                + ", '30' AS "               + ORDER_COL8_STATE
                + ", 'Mr Jhon' AS "               + ORDER_COL9_CONTACT
                + ", '0213456' AS "               + ORDER_COL10_PHONE
                + " UNION SELECT 102, 3, 2, '28/10/2018', '03/11/2018',"
                + " 1280, '20/09/2018', '20', 'Miss Apple', '0213456'";
        Log.d(TAG, "init  order ............: " + query_order_init);
        db.execSQL(query_order_init);

        String query_photo_init = "INSERT INTO "    + PHOTO_TABLE_NAME
                + " SELECT 10001 AS "               + PHOTO_COL1_PID
                + ", 1 AS "                         + PHOTO_COL2_CID
                + ", 3 AS "                         + PHOTO_COL3_UID
                + ", '12/09/2018' AS "              + PHOTO_COL4_DATE
                + ", 'https://www.dike.lib.ia.us/images/sample-1.jpg' AS " + PHOTO_COL5_PATH
                + ", 'some description' AS "        + PHOTO_COL6_DESC
                + ", 0 AS "        + PHOTO_COL7_DEL
                + " UNION SELECT 10002, 1, 4, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-2.jpg', '', 0"
                + " UNION SELECT 10003, 1, 4, '12/10/2018', 'https://www.dike.lib.ia.us/images/sample-3.jpg', '', 0"
                + " UNION SELECT 10004, 2, 2, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-4.jpg', '', 0"
                + " UNION SELECT 10005, 2, 3, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-5.jpg', '', 0"
                + " UNION SELECT 10006, 2, 3, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-1.jpg', '', 0"
                + " UNION SELECT 10007, 2, 4, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-2.jpg', '', 0"
                + " UNION SELECT 10008, 3, 2, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-4.jpg', '', 0"
                + " UNION SELECT 10009, 3, 3, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-3.jpg', '', 0"
                + " UNION SELECT 10010, 3, 3, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-2.jpg', '', 0"
                + " UNION SELECT 10011, 4, 2, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-3.jpg', '', 0"
                + " UNION SELECT 10012, 4, 3, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-2.jpg', '', 0"
                + " UNION SELECT 10013, 4, 4, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-5.jpg', '', 0"
                + " UNION SELECT 10014, 4, 4, '28/10/2018', 'https://www.dike.lib.ia.us/images/sample-4.jpg', '', 0"
                ;

        Log.d(TAG, "init  photo ............: " + query_photo_init);
        db.execSQL(query_photo_init);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String str_drop = "DROP TABLE IF EXISTS ";
        db.execSQL(str_drop + USER_TABLE_NAME);
        db.execSQL(str_drop + VEHICLE_TABLE_NAME);
        db.execSQL(str_drop + CAMP_TABLE_NAME);
        db.execSQL(str_drop + ORDER_TABLE_NAME);
        db.execSQL(str_drop + PHOTO_TABLE_NAME);
        this.onCreate(db);
    }
}
