package com.jdjr.datasolution.util;

import com.jdjr.datasolution.domain.JXWGoodsDO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by xiaodengliang on 2016/5/18.
 * 功能：解析具体店铺页
 */
public class ParseJxwUtil {

    public static JXWGoodsDO jxwGoodsDO = new JXWGoodsDO();//以数据包的形式返回

    /**
     * 解析名称，销量，评分
     *
     * @param pageContent
     * @return
     * @throws Exception
     */
    public static JXWGoodsDO parseIntroInfo(String pageContent) throws Exception {
        String comSales = "";
        String comScore = "";
        Document document = Jsoup.parse(pageContent);
        Elements infoElement = document.getElementsByClass("w1200");
        try {
            Elements elements = infoElement.get(0).getElementsByClass("introInfo");
            Element nameElement = elements.get(0).child(0);
            Element salesElement = elements.get(0).child(2);
            Elements h2Elements = nameElement.getElementsByTag("h2");
            Elements comElements = salesElement.getElementsByTag("li");
            comSales = comElements.get(0).select("em").text();
            comScore = comElements.get(1).select("em").text();
            if (h2Elements != null) {
                jxwGoodsDO.setGoods_name(h2Elements.text().trim());//解析出商品的名称
            }
            if (comSales != null) {
                jxwGoodsDO.setTotal_sales(comSales.trim());//解析出累计销量
            }
            if (comScore != null) {
                jxwGoodsDO.setScore(comScore.trim());//解析出酒友评分
            }
            return jxwGoodsDO;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析店规格等基本
     *
     * @param pageContent
     * @return
     * @throws Exception
     */
    public static JXWGoodsDO parseIntrBox(String pageContent) throws Exception {
        Document document = Jsoup.parse(pageContent);
        Elements boxElement = document.getElementsByClass("w1200");
        try {
            Elements elements = boxElement.get(0).getElementsByClass("intrBox");
            Elements elements1 = boxElement.get(0).getElementsByClass("d-tab");
            Elements placeElements = elements.get(0).getElementsContainingOwnText("产地");
            if (placeElements != null){
                jxwGoodsDO.setPlace_of_origin(placeElements.select("em").text());
            }
            Elements qualityElements = elements.get(0).getElementsContainingOwnText("净含量");
            if (qualityElements != null){
                jxwGoodsDO.setNet_quantity(qualityElements.select("em").text());
            }
            Elements addrElements = elements.get(0).getElementsContainingOwnText("酒厂");
            if (addrElements != null){
                jxwGoodsDO.setAddress(addrElements.select("em").text());
            }

            Elements degElements = elements.get(0).getElementsContainingOwnText("酒精度");
            if (degElements != null){
                jxwGoodsDO.setDegree(degElements.select("em").text());
            }
            Elements specElements = elements.get(0).getElementsContainingOwnText("规格");
            if (specElements != null){
                jxwGoodsDO.setSpec(specElements.select("em").text());
            }
            Elements boxElements = elements.get(0).getElementsContainingOwnText("箱规");
            if (boxElements != null){
                jxwGoodsDO.setBox_capacity(boxElements.select("em").text());
            }
            Elements flavorElements = elements.get(0).getElementsContainingOwnText("香型");
            if (flavorElements != null){
                jxwGoodsDO.setFlavor(flavorElements.select("em").text());
            }
            Elements commentsElement = elements1.get(0).getElementsContainingOwnText("累计评价");
            if (commentsElement != null){
                jxwGoodsDO.setTatal_comments(commentsElement.select("span").text());
            }
            return jxwGoodsDO;
        } catch (Exception e) {
            return null;
        }
    }
}
