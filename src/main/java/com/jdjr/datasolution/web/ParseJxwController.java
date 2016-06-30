package com.jdjr.datasolution.web;

import com.jdjr.datasolution.domain.JXWGoodsDO;
import com.jdjr.datasolution.service.ISaveJxwService;
import com.jdjr.datasolution.service.impl.SaveJxwServiceImpl;
import com.jdjr.datasolution.util.DateUtil;
import com.jdjr.datasolution.util.ParseJxwUtil;
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
@RequestMapping("/jxw")
public class ParseJxwController {
    /**
     * 数据入库主函数
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseJxwController.class.getName());

    JXWGoodsDO jxwGoodsDO = new JXWGoodsDO();
    SettingsUtil settings = null;
    @Resource
    ISaveJxwService saveJxwService;


    @RequestMapping("/insertDB")
    public void runMain() throws Exception {
        settings = new SettingsUtil(new File("input/settings.properties"));//实例化参数处理类
        settings.setParameters();
        String existingName = FileUtils.readFileToString(new File("log/insertDB.log"), "UTF-8");
        LOGGER.warn("开始入库--->>>>");
        String dir_name = null;                            //子文件夹名
        File file = new File(settings.getFileSavePath());  //读取父文件夹
        File dir_file[] = null;                            //存放子文件夹下面的html文件
        File files[] = file.listFiles();                   //读取父目录下面的所有子目录文件夹
        int lens = files.length;                           //父文件夹下面的文件（夹）长度
        for (int i = 0; i < lens; i++) {
            dir_name = files[i].getName();                  //获得子文件夹名字
            dir_file = files[i].listFiles();
            for (File htmlFile : dir_file) {
                if (existingName.contains(dir_name + "-" + htmlFile.getName())) {
                    continue;
                } else {
                    int successFlag = savaData(htmlFile, dir_name,settings.getFileSavePath());
                    if (successFlag > 0) {//表示这条数据入库成功
                        LOGGER.warn("页面" + dir_name + "-" + htmlFile.getName() + "已经解析入库");
                        FileUtils.write(new File("log/insertDB.log"), dir_name + "-" + htmlFile.getName() + "\n", "UTF-8", true);
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
    public int savaData(File htmlFile, String dir_name,String filePath) throws Exception {
        String fileContent = "";;
        fileContent = FileUtils.readFileToString(htmlFile, "UTF-8");
        jxwGoodsDO = ParseJxwUtil.parseIntroInfo(fileContent);
        jxwGoodsDO = ParseJxwUtil.parseIntrBox(fileContent);
        try {
            // 暂存基本数据-------------------------
            jxwGoodsDO.setBrand_name(dir_name);
            jxwGoodsDO.setGoods_id(htmlFile.getName().substring(0, htmlFile.getName().indexOf(".")));
            jxwGoodsDO.setGoods_url("http://www.jiuxian.com/goods-" + htmlFile.getName());
            jxwGoodsDO.setFile_path(filePath + "/" + dir_name + "/" + htmlFile.getName());
            jxwGoodsDO.setOperate_time(DateUtil.getStringDateByFormat(new Date(), "yyyy/MM/dd"));
            int i = saveJxwService.insertData(jxwGoodsDO);
            return i;
        } catch (Exception e) {
            LOGGER.error("保存数据失败");
            e.printStackTrace();
            return 0;
        }
    }
}
