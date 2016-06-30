package com.jdjr.datasolution.service.impl;

import com.jdjr.datasolution.dao.DpDAO;
import com.jdjr.datasolution.domain.DPShopDO;
import com.jdjr.datasolution.service.ISaveDpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiaodengliang on 2016/5/18.
 */
@Service
public class SaveDpServiceImpl implements ISaveDpService {
    @Resource
    DpDAO dpDAO;

    public int getRecord() {
        return dpDAO.getRecord();
    }

    /**
     * 插入数据，首先需要对开店日期做一些处理
     *
     * @param dpShopDO
     * @return
     */
    @Override
    public int insertData(DPShopDO dpShopDO) {
        if (!StringUtils.isEmpty(dpShopDO.getOpening_date())) {
            String newFormatDate = dpShopDO.getOpening_date().replace("-", "/");
            String firstString = newFormatDate.substring(0, newFormatDate.indexOf("/"));
            int firstNum = Integer.parseInt(firstString);
            if (firstNum > 16) {
                dpShopDO.setOpening_date("19" + newFormatDate);
                return dpDAO.saveData(dpShopDO);
            } else {
                dpShopDO.setOpening_date("20" + newFormatDate);
                return dpDAO.saveData(dpShopDO);
            }
        }else {
            dpShopDO.setOpening_date("");
            return dpDAO.saveData(dpShopDO);
        }
    }

    @Override
    public String findShopUrlById(int id) {
        List<DPShopDO> dpShopDO = dpDAO.findShopById(id);//根据id获得每个店铺的信息
        return dpShopDO.get(0).getShop_url();
    }

    @Override
    public List<DPShopDO> findShopById(int id) {
        List<DPShopDO> dpShopDO = dpDAO.findShopById(id);
        return dpShopDO;
    }

    @Override
    public void addMonthComment(String monthComment, int id) {
        dpDAO.insertMonthComment(monthComment, id);
    }

    @Override
    public void addLngAndLat(String longitude, String latitude, int id) {
        dpDAO.insertLngAndLat(longitude,latitude,id);
    }
}
