package com.jdjr.datasolution.web;

import com.jdjr.datasolution.param.InfoParam;
import com.jdjr.datasolution.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.File;

/**
 * Created by xiaodengliang on 2016/5/30.
 * 点评数据平台
 */
@Controller
@RequestMapping("/dp")
public class CrawlerDpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerDpController.class.getName());

    @RequestMapping(value = {"/crawler"})
    public static String crawlerDp(Model m) throws Exception {
        InfoParam infoParam;
        try {
            infoParam = CrawlerDpUtil.runMain();
            m.addAttribute("brandName", infoParam.getBrandName());
            m.addAttribute("filePath", infoParam.getFilePath());
            m.addAttribute("startTime", infoParam.getStartTime());
            m.addAttribute("endTime", infoParam.getEndTime());
            return "dataPlatform/crawlerDpStatus";
        } catch (Exception e) {
            LOGGER.error("抓取失败");
            return "error";
        }
    }

}
