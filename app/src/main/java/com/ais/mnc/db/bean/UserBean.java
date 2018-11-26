package com.ais.mnc.db.bean;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 20/10/2018
 * @author Shaw
 * @version 1.0
 */
public class UserBean {
//    //USER TABLE CONSTANTS
//    public static final String USER_TABLE_NAME = "tbUser";
//    public static final String USER_COL1_UID = "UserID";
//    public static final String USER_COL2_NAME = "Username";
//    public static final String USER_COL3_EMAIL = "Email";
//    public static final String USER_COL4_PWD = "Password";
//    public static final String USER_COL5_TYPE = "Usertype";

    private int uid;
    private String uname;
    private String email;
    private String password;

    public UserBean() {

    }

    public UserBean(int pUid, String pUname, String pEmail, String pPassword) {
        uid = pUid;
        uname = pUname;
        email = pEmail;
        password = pPassword;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String pUname) {
        uname = pUname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String pEmail) {
        email = pEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pPassword) {
        password = pPassword;
    }
}
