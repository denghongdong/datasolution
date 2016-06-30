package com.jdjr.datasolution.web;

import com.jdjr.datasolution.domain.DPShopDO;
import com.jdjr.datasolution.service.ISaveDpService;
import com.jdjr.datasolution.util.DateUtil;
import com.jdjr.datasolution.util.ParseDpUtil;
import com.jdjr.datasolution.util.SettingsUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

/**
 * Created by xiaodengliang on 2016/5/18.
 */
@Controller
@RequestMapping("/dp")
public class ParseDpController {
    /**
     * 数据入库主函数
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseDpController.class.getName());

    DPShopDO dpShopDO = new DPShopDO();
    SettingsUtil settings = null;

    @Resource
    ISaveDpService saveDpService;

    @RequestMapping("/insertDB")
    public void runMain() throws Exception {
        String dir_name = null;                            //子文件夹名
        File file = new File(settings.getFileSavePath());  //读取父文件夹
        File dir_file[] = null;                            //存放子文件夹下面的html文件
        settings = new SettingsUtil(new File("input/settings.properties"));//实例化参数处理类
        settings.setParameters();                  //加载配置文件
        String existingName = FileUtils.readFileToString(new File("log/insertDB.log"), "UTF-8");
        LOGGER.warn("开始入库--->>>>");
        File files[] = file.listFiles();                   //读取父目录下面的所有子目录文件夹
        int lens = files.length;                           //父文件夹下面的文件（夹）长度
        for (int i = 0; i < lens; i++) {
            dir_name = files[i].getName();                  //获得子文件夹名字
            dir_file = files[i].listFiles();
            for (File htmlFile : dir_file) {
                if (existingName.contains(dir_name +"-"+ htmlFile.getName())) {
                    continue;
                } else {
                    int successFlag = savaData(htmlFile, dir_name);
                    if (successFlag > 0) {//表示这条数据入库成功
                        LOGGER.warn("页面" + dir_name +"-"+ htmlFile.getName() + "已经解析入库");
                        FileUtils.write(new File("log/insertDB.log"), dir_name +"-"+ htmlFile.getName() + "\n", "UTF-8", true);
                    } else {
                        LOGGER.warn(dir_name + "******" + htmlFile.getName() + "入库失败");
                        System.exit(0);
                    }
                }
            }
            LOGGER.warn("-------------已经解析完" + dir_name + "的所有页面------------");
            FileUtils.write(new File("log/insertDB.log"), dir_name + "\n", "UTF-8", true);
        }
    }

    /**
     * 保存数据
     *
     * @param htmlFile
     * @param dir_name
     * @return
     */

    public int savaData(File htmlFile, String dir_name) throws Exception {
        String shopAddr = "";
        String info = "";
        String fileContent = "";
        String rediUrl = "";
        String openingDate = "";
        String shopUrl = "";
        String brandName = "";
        String shopName = "";
        shopUrl = "http://www.dianping.com/shop/" + htmlFile.getName();
        rediUrl = shopUrl.substring(0, shopUrl.lastIndexOf(".html")) + "/editmember";
        brandName = settings.getFileSavePath().substring(settings.getFileSavePath().indexOf("_") + 1,
                settings.getFileSavePath().lastIndexOf("_"));
        fileContent = FileUtils.readFileToString(htmlFile, "UTF-8");
        try {
            info = ParseDpUtil.parseHtmlInfo(fileContent);//解析评论数、评分等
            shopAddr = ParseDpUtil.parseHtmlAddress(fileContent);//解析地址
            openingDate = ParseDpUtil.parseHtmlOpeningData(rediUrl);//解析开店日期
            shopName = ParseDpUtil.parseHtmlShopName(fileContent);//解析店铺名称
            // 暂存基本数据-------------------------
            dpShopDO.setCity_enName(dir_name.substring(dir_name.indexOf("-") + 1, dir_name.lastIndexOf("-")));
            dpShopDO.setCityId(dir_name.substring(0, dir_name.indexOf("-")));
            dpShopDO.setCity_name(dir_name.substring(dir_name.lastIndexOf("-") + 1));
            dpShopDO.setShopId(htmlFile.getName().substring(0, htmlFile.getName().lastIndexOf(".")));
            dpShopDO.setShop_name(shopName);
            dpShopDO.setShop_url(shopUrl);
            dpShopDO.setFile_path(settings.getFileSavePath() + "\\" + dir_name);
            dpShopDO.setBrand_name(brandName);
            dpShopDO.setOperate_time(DateUtil.getStringDateByFormat(new Date(), "yyyy/MM/dd"));

            //暂存解析出来的数据
            if (openingDate != null && openingDate != "") {
                dpShopDO.setOpening_date(openingDate);
            } else {
                dpShopDO.setOpening_date("");
            }
            if (shopAddr != null && shopAddr != "") {
                dpShopDO.setShop_address(shopAddr.substring(shopAddr.lastIndexOf("： ") + 1));
            }
            if (info != null && info != "") {
                dpShopDO = doInfoString(info);
            }
            int i = saveDpService.insertData(dpShopDO);
            return i;
        } catch (Exception e) {
            LOGGER.error("保存数据失败");
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 对info字符串进行处理
     *
     * @param info
     * @return
     */
    public DPShopDO doInfoString(String info) {
        //String name = "2条评论 人均：- 口味：6.8 环境：6.8 服务：6.8";
        //64条评论 人均：81元 | 口味：8.9 | 环境：9.2 | 服务：9.1
        String[] infoString = info.split(" ");
        for (String infoTxt : infoString) {
            if (infoTxt.endsWith("评论")) {
                dpShopDO.setComments_num(infoTxt.substring(0, infoTxt.length() - 3));

            } else if (infoTxt.startsWith("人均") || infoTxt.startsWith("消费")) {
                dpShopDO.setPer_consumption(infoTxt.substring(infoTxt.lastIndexOf("：") + 1));

            } else if (infoTxt.startsWith("口味")|| infoTxt.startsWith("产品")) {
                dpShopDO.setTaste(infoTxt.substring(infoTxt.lastIndexOf("：") + 1));

            } else if (infoTxt.startsWith("环境")) {
                dpShopDO.setSurroundings(infoTxt.substring(infoTxt.lastIndexOf("：") + 1));

            } else if (infoTxt.startsWith("服务")) {
                dpShopDO.setService(infoTxt.substring(infoTxt.lastIndexOf("：") + 1));
            } else {
                dpShopDO.setComments_num("0");
                dpShopDO.setPer_consumption("0");
                dpShopDO.setSurroundings("0");
                dpShopDO.setService("0");
                dpShopDO.setTaste("0");
            }
        }
        return dpShopDO;
    }
}
