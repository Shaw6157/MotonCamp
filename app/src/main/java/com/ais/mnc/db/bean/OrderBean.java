package com.ais.mnc.db.bean;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 24/10/2018
 * @author Shaw
 * @version 1.0
 */
public class OrderBean {

//    //ORDER TABLE CONSTANTS
//    public static final String ORDER_TABLE_NAME = "tbOrder";
//    public static final String ORDER_COL1_OID = "OrderID";
//    public static final String ORDER_COL2_VID = "VehicleID";
//    public static final String ORDER_COL3_UID = "UserID";
//    public static final String ORDER_COL4_DATABG = "DateBegin";
//    public static final String ORDER_COL5_DATAED = "DateEnd";
//    public static final String ORDER_COL6_AMOUNT = "Amount";
//    public static final String ORDER_COL7_DATA = "OrderDate";
//    public static final String ORDER_COL8_STATE = "OrderState";
//    public static final String ORDER_COL9_CONTACT = "ContactName";
//    public static final String ORDER_COL10_PHONE = "ContactPhone";

    private int oid;
    private int vid;
    private int uid;
    private String datebg;
    private String dateed;
    private int amount;
    private String odate;
    private String ostate;
    private String contactName;
    private String contactPhone;

    public OrderBean() {
    }

    public OrderBean(int oid, int vid, int uid, String datebg, String dateed, int amount, String odate, String ostate, String contactName, String contactPhone) {
        this.oid = oid;
        this.vid = vid;
        this.uid = uid;
        this.datebg = datebg;
        this.dateed = dateed;
        this.amount = amount;
        this.odate = odate;
        this.ostate = ostate;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDatebg() {
        return datebg;
    }

    public void setDatebg(String datebg) {
        this.datebg = datebg;
    }

    public String getDateed() {
        return dateed;
    }

    public void setDateed(String dateed) {
        this.dateed = dateed;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOdate() {
        return odate;
    }

    public void setOdate(String odate) {
        this.odate = odate;
    }

    public String getOstate() {
        return ostate;
    }

    public void setOstate(String ostate) {
        this.ostate = ostate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
