package com.ais.mnc.db.bean;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 25/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class PhotoBean {

//    //PHOTO TABLE CONSTANTS
//    public static final String PHOTO_TABLE_NAME = "tbPhoto";
//    public static final String PHOTO_COL1_PID = "PhotoID";
//    public static final String PHOTO_COL3_CID = "CampsiteID";
//    public static final String PHOTO_COL2_UID = "UserID";
//    public static final String PHOTO_COL4_DATE = "PhotoDate";
//    public static final String PHOTO_COL5_PATH = "Path";
//    public static final String PHOTO_COL6_DESC = "Desc";
//    public static final String PHOTO_COL7_DEL = "DelFlag";


    private int pid;
    private int cid;
    private int uid;
    private String date;
    private String path;
    private String desc;
    private int del;

    public PhotoBean() {

    }

    public PhotoBean(int pid, int cid, int uid, String date, String path, String desc, boolean del) {
        this.pid = pid;
        this.cid = cid;
        this.uid = uid;
        this.date = date;
        this.path = path;
        this.desc = desc;
        this.del = del ? 1 : 0;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean getDel() {
        return del != 0;
    }

    public void setDel(boolean del) {
        this.del = del ? 1 : 0;
    }

    public void setDel(int del) {
        this.del = del;
    }

}
