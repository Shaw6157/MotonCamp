package com.ais.mnc.db.dao;

import com.ais.mnc.db.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2018 CYu AIS. All rights reserved.
 * Description:
 * Created on 21/10/2018
 *
 * @author Shaw
 * @version 1.0
 */
public interface UserDao {
    public boolean createUser(UserBean p_user);
    public String getPassword(String p_uname);
    public UserBean findByName(String p_uname);
    //    public ArrayList<UserBean> findByCoreBar(CorePage cp);
    public List<UserBean> findAll();
    public boolean updateUser(UserBean p_user);
    public boolean deleteUser(int uid);
    public boolean checkExist(String p_uname, String p_email);
}
