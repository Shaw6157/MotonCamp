package com.ais.mnc.db.bean;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on:  22/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public class CampBean {
    public static final String TAG = "CampBean";

//    //CAMPSITE TABLE CONSTANTS
//    public static final String CAMP_TABLE_NAME = "tbCampsite";
//    public static final String CAMP_COL1_CID = "CampsiteID";
//    public static final String CAMP_COL2_CNAME = "CampsiteName";
//    public static final String CAMP_COL3_ADDRESS = "Address";
//    public static final String CAMP_COL4_INFO = "Info";
//    public static final String CAMP_COL5_URL = "Url";
//    public static final String CAMP_COL6_IMAGE = "Image";
//    public static final String CAMP_COL7_FEATURES   = "Features";
//    public static final String CAMP_COL8_lAT   = "LAT";
//    public static final String CAMP_COL9_LNG   = "LNG";

    private int cid;
    private String cname;
    private String address;
    private String info;
    private String url;
    private String image;
    private String features;
    private double cLAT;
    private double cLNG;
    private String phone;

    public CampBean() {

    }

    public CampBean(int pCid, String pCname, String pAddress, String pInfo, String pUrl, String pImage, String pFeatures, double LAT, double LNG, String pPhone) {
        cid = pCid;
        cname = pCname;
        address = pAddress;
        info = pInfo;
        url = pUrl;
        image = pImage;
        features = pFeatures;
        this.cLAT = LAT;
        this.cLNG = LNG;
        this.phone = pPhone;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String pCname) {
        cname = pCname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String pAddress) {
        address = pAddress;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String pInfo) {
        info = pInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String pUrl) {
        url = pUrl;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public double getLAT() {
        return cLAT;
    }

    public void setLAT(double LAT) {
        this.cLAT = LAT;
    }

    public double getLNG() {
        return cLNG;
    }

    public void setLNG(double LNG) {
        this.cLNG = LNG;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
