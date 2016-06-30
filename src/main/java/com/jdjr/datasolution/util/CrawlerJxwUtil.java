package com.jdjr.datasolution.util;

import com.jdjr.datasolution.param.InfoParam;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaodengliang on 2016/6/28.
 */
public class CrawlerJxwUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerJxwUtil.class.getName());
    public static SettingsUtil settings = null;
    public static InfoParam infoParam = new InfoParam();
    public CrawlerJxwUtil(File propertiyFile) {
        settings = new SettingsUtil(propertiyFile);//实例化参数处理类
        settings.setParameters();                  //加载配置文件
    }

    public static InfoParam runMain() throws Exception {
        String startTime = "";
        String endTime = "";
        List<String> brandList = FileUtils.readLines(new File("input/wine_brand.txt"), "UTF-8");
        String existingName = FileUtils.readFileToString(new File("log/crawleredBrand.log"), "UTF-8");
        CrawlerJxwUtil infoExtra = new CrawlerJxwUtil(new File("input/settings.properties"));
        infoParam.setFilePath(settings.getFileSavePath());
        startTime = DateUtil.getStringDateByFormat(new Date(), "yyyy/MM/dd  HH-mm-ss");
        infoExtra.getFirstPage(brandList, existingName);
        endTime = DateUtil.getStringDateByFormat(new Date(), "yyyy/MM/dd  HH-mm-ss");
        infoParam.setStartTime(startTime);
        infoParam.setEndTime(endTime);
        FileUtils.write(new File("log/crawleredBrand.log"), "");
        return infoParam;
    }
    /**
     * 一、获取首页
     *
     * @param brandList
     */
    public void getFirstPage(List<String> brandList, String existingName) throws Exception {

        String absUrl = "";
        for (String brand : brandList) {
            absUrl = settings.getInfoUrl() + brand;
            if (existingName.contains(brand)) {
                //判断这个城市是否是已经爬虫过的城市，若是，跳出本次循环，进行下一个城市
                continue;
            } else {//若该城市还没有爬虫，继续下面的流程
                try {
                    LOGGER.warn("开始爬取--" + brand + "--的数据");
                    infoParam.setBrandName(brand);
                    Document document = GetHtmlUtil.getDocument(absUrl);//抓取首页
                    //Thread.sleep(1000);
                    if (document != null) {
                        getListPageUrls(document, brand);//获取列表页
                        LOGGER.warn(brand + "----的数据已经抓取完毕-->>>>>");
                        //保存已经爬取的城市名，便于再次启动时从第一个未开始的城市爬起
                        FileUtils.write(new File("log/crawleredBrand.log"), brand + "\n", "UTF-8", true);
                    } else {
                        continue;
                    }
                } catch (Exception e) {//拨号一下
                    ConnectAdslNet.reconnectAdsl("宽带", "admin", "admin");
                    LOGGER.error("抓取" + brand + "  首页失败");
                }
            }
        }
    }

    /**
     * 二、解析首页，获取列表页，
     * 然后解析列表页，获取店铺URL
     *
     * @param document
     * @return
     * @throws Exception
     */
    public void getListPageUrls(Document document, String brand) throws Exception {
        String listPageUrl = "";
        Element element = document.getElementById("order");
        Elements elements = element.getElementsByTag("p");
        Element spanElement = elements.get(0).child(2);
        String text = spanElement.text();//取span标签的值，即为总页数
        if (text != null && text != "") {
            for (int i = 1; i <= Integer.parseInt(text); i++) {//从p1开始，并且列表页URL
                listPageUrl = "http://list.jiuxian.com/search.htm?" + "pageNum=" + i + "&&key=" + brand;
                Document listPageDoc = GetHtmlUtil.getDocument(listPageUrl);//获取列表页面
                Thread.sleep(2000);
                //解析列表页,获取店铺URL
                getShopUrls(listPageDoc, brand);
            }
        } else {
            return;
        }
    }

    /**
     * 三、解析列表页，获取店铺URL
     *
     * @param document
     * @return
     * @throws Exception
     */
    public String getShopUrls(Document document, String brand) throws Exception {
        try {
            Elements elements = document.getElementsByClass("proListSearch");
            if (elements == null) {
                return null;
            } else {
                Elements select = elements.select("li");
                for (int i = 0; i < select.size(); i++) {
                    Element liElement = select.get(i);
                    if (liElement == null) {
                        return null;
                    } else {
                        Element claElement = liElement.child(1);
                        Elements divElement = claElement.getElementsByClass("collect_box");
                        //Elements priceElement = claElement.getElementsByClass("priceArea");
                        //price = priceElement.select("p").text();
                        Elements aElement = divElement.get(0).getElementsByTag("a");
                        String text = aElement.attr("href");
                        getShopPage(text.trim(), brand);
                    }
                }
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 四、爬虫店铺页
     */
    public void getShopPage(String shopUrl, String brand) throws Exception {
        try {
            if (shopUrl != null && shopUrl != "") {
                Document htmlContent = GetHtmlUtil.getDocument(shopUrl);
                //保存shop页面
                // http://www.jiuxian.com/goods-7027.html
                FileUtils.write(new File(settings.getFileSavePath() + "\\" + brand + "\\" +
                        shopUrl.substring(shopUrl.indexOf("-") + 1)), htmlContent + "\n", "UTF-8", false);
                LOGGER.warn("已经爬取===" + brand + "---" + shopUrl.substring(shopUrl.indexOf("-") + 1));
            } else {
                return;
            }
        } catch (Exception e) {
            ConnectAdslNet.reconnectAdsl("宽带", "admin", "admin");
            LOGGER.error("店铺" + brand + shopUrl + "抓取失败");
        }
    }
}

