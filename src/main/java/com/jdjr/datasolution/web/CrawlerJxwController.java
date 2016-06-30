package com.jdjr.datasolution.web;

import com.jdjr.datasolution.param.InfoParam;
import com.jdjr.datasolution.util.CrawlerJxwUtil;
import com.jdjr.datasolution.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.Date;

/**
 * Created by xiaodengliang on 2016/6/27.
 * 爬虫项目--店铺页面
 */
@Controller
@RequestMapping("/jxw")
public class CrawlerJxwController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerJxwController.class.getName());


    @RequestMapping(value = {"/crawler"})
    public static String crawlerJxw(Model m) throws Exception {
        InfoParam infoParam;
        try {
            infoParam = CrawlerJxwUtil.runMain();
            m.addAttribute("brandName", infoParam.getBrandName());
            m.addAttribute("filePath", infoParam.getFilePath());
            m.addAttribute("startTime", infoParam.getStartTime());
            m.addAttribute("endTime", infoParam.getEndTime());
            FileUtils.write(new File("log/crawleredBrand.log"), "");
            return "dataPlatform/crawlerJxwStatus";
        } catch (Exception e) {
            LOGGER.error("抓取失败");
            return "error";
        }
    }
}
