package com.jdjr.datasolution.service;

import com.jdjr.datasolution.domain.DPShopDO;
import com.jdjr.datasolution.domain.JXWGoodsDO;

import java.util.List;

/**
 * Created by xiaodengliang on 2016/5/26.
 */
public interface ISaveDpService {

    public int getRecord();

    public int insertData(DPShopDO dpShopDO);

    public String findShopUrlById(int id);

    public List<DPShopDO> findShopById(int id);

    public void addMonthComment(String monthComment, int id);

    public void addLngAndLat(String longitude, String latitude, int id);
}
