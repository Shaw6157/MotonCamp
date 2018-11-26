package com.ais.mnc.db.bean;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 20/10/2018
 * @author Shaw
 * @version 1.0
 */
public class VehicleBean {


//    //VEHICLE TABLE CONSTANTS
//    public static final String VEHICLE_TABLE_NAME = "tbVehicle";
//    public static final String VEHICLE_COL1_VID   = "VehicleID";
//    public static final String VEHICLE_COL2_VNAME = "VehicleName";
//    public static final String VEHICLE_COL3_TYPE  = "Type";
//    public static final String VEHICLE_COL4_TRANSMISSION  = "Transmission";
//    public static final String VEHICLE_COL5_YEAR  = "Year";
//    public static final String VEHICLE_COL6_ENGIN  = "Engine";
//    public static final String VEHICLE_COL7_PRICE = "Price";
//    public static final String VEHICLE_COL8_IMAGE = "Image";
//    public static final String VEHICLE_COL9_INFO  = "Info";
//    public static final String VEHICLE_COL10_MODEL = "Model";
//    //TODO 54 cols more


    private int vid;
    private String vname;
    private String type;
    private String transmission;
    private String year;
    private String engin;
    private int price;
    private String image;
    private String info;
    private String model;
    private int feature0;
    private int feature1;

    public VehicleBean() {

    }

    public VehicleBean(int vid, String vname, String type, String transmission, String year, String engin, int price, String image, String info, String model, boolean[] features) {
        this.vid = vid;
        this.vname = vname;
        this.type = type;
        this.transmission = transmission;
        this.year = year;
        this.engin = engin;
        this.price = price;
        this.image = image;
        this.info = info;
        this.model = model;
        if (features != null && features.length > 0) {
            feature0 = features[0] ? 1 : 0;
            feature1 = features[1] ? 1 : 0;
        }
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEngin() {
        return engin;
    }

    public void setEngin(String engin) {
        this.engin = engin;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean getFeature0() {
        return feature0 != 0;
    }

    public void setFeature0(boolean pFeature0) {
        feature0 = pFeature0 ? 1 : 0;;
    }

    public boolean getFeature1() {
        return feature1 != 0;
    }

    public void setFeature1(boolean pFeature1) {
        feature1 = pFeature1 ? 1 : 0;;
    }
}
