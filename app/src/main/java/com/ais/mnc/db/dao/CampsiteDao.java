package com.ais.mnc.db.dao;

import com.ais.mnc.db.bean.CampBean;

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
public interface CampsiteDao {
    public boolean createCampsite(CampBean p_campsite);
    public boolean updateCampsite(CampBean p_campsite);
    public boolean deleteCampsite(int cid);
    public CampBean findById(int cid);
    //    public ArrayList<CampBean> findByCoreBar(CorePage cp);
    public List<CampBean> findAll();
}
