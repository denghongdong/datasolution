package com.jdjr.datasolution.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaodengliang on 2016/5/26.
 * 功能：爬虫网页
 */
public class GetHtmlUtil extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetHtmlUtil.class.getName());

    /**
     * 对于给定的URL，通过Jsoup获取document对象
     */
    public static Document getDocument(String url) throws Exception {
        Document document = null;
        try {
            Thread.sleep(3000);
            document = Jsoup.connect(url).
                    data("query", "Java").
                    userAgent("Mozilla").
                    cookie("auth", "token").
                    timeout(30000).get();
        } catch (Exception e) {
            if (e.getMessage().contains("HTTP error fetching URL")) {
                LOGGER.error(">>>遇到防爬问题，需要暂停10分钟");
                Thread.sleep(1000 * 60 * 10);//遇到403暂停10分钟
            } else if (e.getMessage().contains("Read timed out")) {
                Thread.sleep(1000 * 60);
                getDocument(url);
            }else {
                LOGGER.error(e.getMessage());
            }
            e.printStackTrace();
        }
        return document;
    }
}
