package com.jdjr.datasolution.helper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by xiaodengliang on 2016/5/24.
 * 功能：过滤数据
 */
public class DeleteNoShops {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteNoShops.class.getName());

    public static void main(String[] args) throws Exception {
        String fileContent = "";
        String resultString = "";
        try {
            File file = new File("E:\\爬虫数据\\20160612\\dp_xiabu_data");
            File[] files = file.listFiles();
            int lens = files.length;
            for (int i = 0; i < lens; i++) {
                File[] htmlFile = files[i].listFiles();
                my:
                for (File htmlF : htmlFile) {
                    fileContent = FileUtils.readFileToString(htmlF, "UTF-8");
                    resultString = parseHtmlInfo(fileContent);
                    if (!StringUtils.isEmpty(resultString)) {
                        if (resultString.startsWith("呷哺呷哺")) {
                            continue my;
                        } else {
                            htmlF.delete();
                            LOGGER.warn("已经删除了" + htmlF.getName());
                        }
                    } else {
                        htmlF.delete();
                        LOGGER.warn("已经删除了" + htmlF.getName());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String parseHtmlInfo(String pageContent) throws Exception {
        try {
            Document document = Jsoup.parse(pageContent);
            // Element element = document.getElementById("basic-info");
            Element element = document.getElementById("top");
            String resultString = "";
            if (element == null) {
                return null;
            } else {
                Elements h1Element = element.getElementsByTag("h1");
                resultString = h1Element.text();
            }
            return resultString;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
