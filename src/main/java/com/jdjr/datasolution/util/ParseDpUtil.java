package com.jdjr.datasolution.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by xiaodengliang on 2016/5/18.
 * 功能：解析具体店铺页
 */
public class ParseDpUtil {
    /**
     * 解析评论数，评分等
     *
     * @param pageContent
     * @return
     * @throws Exception
     */
    public static String parseHtmlInfo(String pageContent) throws Exception {
        try {
            Document document = Jsoup.parse(pageContent);
            Element element = document.getElementById("basic-info");
            String resultString = "";
            if (element == null) {
                return null;
            } else {
                Elements elementsBriefInfo = element.getElementsByClass("brief-info");
                resultString = elementsBriefInfo.get(0).getElementsByTag("span").text();
            }
            return resultString;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }


    /**
     * 解析地址
     * @param pageContent
     * @return
     * @throws Exception
     */
    public static String parseHtmlAddress(String pageContent) throws Exception {
        try {
            Document document = Jsoup.parse(pageContent);
            Element element = document.getElementById("basic-info");
            String resultString = "";
            if (element == null) {
                return null;
            } else {
                Elements elementsAddress = element.getElementsContainingText("地址：");
                if (elementsAddress != null) {
                    resultString = elementsAddress.get(1).getElementsByTag("span").text();
                }else {
                    return null;
                }
            }
            return resultString;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    /**
     * 解析店铺创建时间
     *
     * @param rediUrl
     * @return
     */
    public static String parseHtmlOpeningData(String rediUrl) throws Exception{
        String spanString = "";
        String resultString = "";
        Thread.sleep(1000);
        try {
            Document document = GetHtmlUtil.getDocument(rediUrl);//爬虫页面
            Element element = document.getElementById("top");
            if (element == null) {
                return null;
            } else {
                Elements select = element.select("div[class=main page-sa page-shop Fix]");
                Elements classElement = select.get(0).children();
                if (classElement != null) {
                    Elements bloElement = classElement.get(0).select("div[class=block raw-block]");
                    Elements liElement = bloElement.get(0).select("ul[class=block-inner desc-list contribute-list Fix]");
                    spanString = liElement.get(0).children().get(0).getElementsByTag("li").text();
                    resultString = spanString.substring(spanString.length() - 8);
                    return resultString;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 解析店铺名称
     * @param pageContent
     * @return
     * @throws Exception
     */
    public static String parseHtmlShopName(String pageContent) throws Exception {
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
            return null;
        }
    }
}
