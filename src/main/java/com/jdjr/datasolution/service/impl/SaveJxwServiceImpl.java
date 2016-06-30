package com.jdjr.datasolution.service.impl;

import com.jdjr.datasolution.dao.JxwDAO;
import com.jdjr.datasolution.domain.JXWGoodsDO;
import com.jdjr.datasolution.service.ISaveJxwService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by xiaodengliang on 2016/5/18.
 */
@Service
public class SaveJxwServiceImpl implements ISaveJxwService {
    @Resource
    JxwDAO jxwDAO;
    /**
     * @param jxwGoodsDO
     * @return
     */
    @Override
    public int insertData(JXWGoodsDO jxwGoodsDO) {
       return jxwDAO.saveData(jxwGoodsDO);
    }

}
