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
 * Created by xiaodengliang on 2016/6/30.
 */
public class CrawlerDpUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerDpUtil.class.getName());
    public static SettingsUtil settings = null;
    public static InfoParam infoParam = new InfoParam();

    public CrawlerDpUtil(File propertiyFile) {
        settings = new SettingsUtil(propertiyFile);//实例化参数处理类
        settings.setParameters();                  //加载配置文件
    }

    //爬虫入口
    public static InfoParam runMain() throws Exception {
        String startTime = "";
        String endTime = "";
        try {
            List<String> cityList = FileUtils.readLines(new File("input/city.txt"), "UTF-8");
            String existingName = FileUtils.readFileToString(new File("log/crawleredCity.log"), "UTF-8");
            CrawlerDpUtil infoExtra = new CrawlerDpUtil(new File("input/settings.properties"));
            infoParam.setFilePath(settings.getFileSavePath());
            startTime = DateUtil.getStringDateByFormat(new Date(), "yyyy/MM/dd  HH-mm-ss");
            infoExtra.getFirstPage(cityList, existingName);//开始入口
            endTime = DateUtil.getStringDateByFormat(new Date(), "yyyy/MM/dd  HH-mm-ss");
            infoParam.setStartTime(startTime);
            infoParam.setEndTime(endTime);
            FileUtils.write(new File("log/crawleredCity.log"), "");//爬虫完毕清空log
            return infoParam;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 一、获取首页
     *
     * @param cityList
     */
    public void getFirstPage(List<String> cityList, String existingName) throws Exception {
        String intoUrl = "";
        String cityId = "";
        String cityName = "";
        for (String city : cityList) {
            cityId = city.substring(0, city.indexOf("-"));
            intoUrl = settings.getFirstUrl() + cityId + settings.getEndUrl();
            cityName = city.substring(city.lastIndexOf("-") + 1);
            if (existingName.contains(cityName)) {
                //判断这个城市是否是已经爬虫过的城市，若是，跳出本次循环，进行下一个城市
                continue;
            } else {//若该城市还没有爬虫，继续下面的流程
                try {
                    LOGGER.warn("开始爬取--" + cityName + "--的数据");
                    Document document = GetHtmlUtil.getDocument(intoUrl);//抓取首页
                    Thread.sleep(1000);
                    if (document != null) {
                        getListPageUrls(document, city, cityId);//获取列表页
                        LOGGER.warn(cityName + "----的数据已经抓取完毕-->>>>>");
                        //保存已经爬取的城市名，便于再次启动时从第一个未开始的城市爬起
                        FileUtils.write(new File("log/crawleredCity.log"), cityName + "\n", "UTF-8", true);
                    } else {
                        //FileUtils.write(new File("log/crawleredCity.log"), cityName + "\n", "UTF-8", true);
                        continue;
                    }
                } catch (Exception e) {//拨号一下
                    ConnectAdslNet.reconnectAdsl("宽带", "admin", "admin");
                    LOGGER.error("抓取" + city + "  首页失败");
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
    public void getListPageUrls(Document document, String city, String cityId)
            throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        String listPageUrl = "";
        String shopUrl = "";
        Elements elements = document.getElementsByClass("page");
        if (elements != null && !elements.isEmpty()) {//存在多列表页
            Elements select = elements.select("a");//获得所有a标签
            Element element = select.get(select.size() - 2);//取倒数第二个a标签，里面包含总页数
            if (element != null) {
                String text = element.text();//取a标签的值，即为总页数
                if (text != null && text != "") {
                    for (int i = 1; i <= Integer.parseInt(text); i++) {//从p1开始，并且列表页URL
                        listPageUrl = settings.getFirstUrl() + cityId + settings.getEndUrl() + "p" + i;
                        Document listPageDoc = GetHtmlUtil.getDocument(listPageUrl);//获取列表页面
                        Thread.sleep(1000);
                        //解析列表页,获取店铺URL
                        shopUrl = getShopUrls(listPageDoc, city);
                        stringBuilder.append(shopUrl + "\n");
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } else {//表示只有首页
            shopUrl = getShopUrls(document, city);
            if (shopUrl != null) {
                stringBuilder.append(shopUrl + "\n");
            } else {
                return;
            }
        }
        //保存店铺URL
        FileUtils.write(new File(settings.getFileSavePath() + "\\" + city + "\\" + city + ".txt"),
                stringBuilder.toString() + "\n", "UTF-8", true);
    }

    /**
     * 三、解析列表页，获取店铺URL
     *
     * @param document
     * @return
     * @throws Exception
     */
    public String getShopUrls(Document document, String city) throws Exception {
        Element element = document.getElementById("shop-all-list");
        StringBuilder stringBuilder = new StringBuilder();
        if (element == null) {
            return null;
        } else {
            Elements select = element.select("li");
            for (int i = 0; i < select.size(); i++) {
                Element liElement = select.get(i);
                if (liElement == null) {
                    return null;
                } else {
                    Element claElement = liElement.child(0);
                    Elements aElement = claElement.getElementsByTag("a");
                    String text = aElement.attr("href");
                    getShopPage(settings.getHost_name() + text, city);
                    stringBuilder.append(settings.getHost_name() + text + "\n");
                }
            }
            return stringBuilder.toString();
        }
    }

    /**
     * 四、爬虫店铺页
     */
    public void getShopPage(String shopUrl, String city) throws Exception {
        try {
            if (shopUrl != null && shopUrl != "") {
                Document htmlContent = GetHtmlUtil.getDocument(shopUrl);
                Thread.sleep(1000);
                //保存shop页面
                FileUtils.write(new File(settings.getFileSavePath() + "\\" + city + "\\" + shopUrl.substring(29) + ".html"),
                        htmlContent + "\n", "UTF-8", false);
                LOGGER.warn("已经爬取===" + city + "---" + shopUrl.substring(29) + ".html");
            } else {
                return;
            }
        } catch (Exception e) {
            ConnectAdslNet.reconnectAdsl("宽带", "admin", "admin");
            LOGGER.error("店铺" + city + shopUrl + "抓取失败");
        }
    }
}

