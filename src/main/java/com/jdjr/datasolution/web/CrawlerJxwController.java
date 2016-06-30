package com.jdjr.datasolution.web;

import com.jdjr.datasolution.util.CrawlerDpUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * Created by xiaodengliang on 2016/6/27.
 * 爬虫项目--店铺页面
 */
@Controller
@RequestMapping("/jxw")
public class CrawlerJxwController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerJxwController.class.getName());

    @RequestMapping(value = {"/crawler"})
    public static void runMain() throws Exception {
        try {
            LOGGER.warn("开始爬虫----");
            CrawlerDpUtil.runMain();
            LOGGER.warn("爬虫结束****");
            FileUtils.write(new File("log/crawleredBrand.log"), "");
        } catch (Exception e) {
            LOGGER.error("抓取失败");
        }
    }

}
