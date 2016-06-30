package com.jdjr.datasolution.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Properties;

/**
 * 处理.property文件，读取用户配置
 * Created by xiaodengliang on 2016/5/30.
 */
public class SettingsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingsUtil.class);
    private String fileSavePath = null;             //用户配置的工作路径
    private String host_name = null;                //用户配置的域名
    private String firstUrl = null;                 //首页URL前半部分
    private String endUrl = null;                   //首页URL后半部分
    private File propfile = null;                   //配置文件
    private String infoUrl = null;                  //酒仙网入口url

    public SettingsUtil(File propfile) {            //有参构造
        this.propfile = propfile;
    }

    /**
     * 通过配置文件设置
     *
     * @throws Exception
     */
    public void setParameters() {
        Properties properties = new Properties();
        InputStreamReader isr;
        try {
            isr = new InputStreamReader(new FileInputStream(propfile), "utf-8");
            properties.load(isr);
            isr.close();
            setParameters((Hashtable) properties);
        } catch (Exception e) {
            LOGGER.error("配置出错！");
            e.printStackTrace();
        }
    }

    /**
     * 通过Map配置
     *
     * @param parameters
     * @throws Exception
     */
    public void setParameters(Hashtable parameters) throws Exception {
        if (firstUrl == null) {
            try {
                firstUrl = (String) parameters.get("firstUrl");
            } catch (Exception e) {
                LOGGER.error("对不起，您配置的首页地址有误！" + e);
                return;
            }
        }
        if (endUrl == null) {
            try {
                endUrl = (String) parameters.get("endUrl");
            } catch (Exception e) {
                LOGGER.error("对不起，您配置的首页地址有误！" + e);
                return;
            }
        }
        if (fileSavePath == null) {
            try {
                fileSavePath = (String) parameters.get("fileSavePath");
            } catch (Exception e) {
                LOGGER.error("对不起，您配置的工作路径有误！" + e);
                return;
            }
        }
        if (host_name == null) {
            try {
                host_name = (String) parameters.get("host_name");
            } catch (Exception e) {
                LOGGER.error("对不起，您配置的域名有误！" + e);
                return;
            }
        }
        if ( infoUrl== null) {
            try {
                infoUrl = (String) parameters.get("infoUrl");
            } catch (Exception e) {
                LOGGER.error("对不起，您配置的地址有误！" + e);
                return;
            }
        }
    }

    public String getEndUrl() {
        return endUrl;
    }

    public String getFirstUrl() {
        return firstUrl;
    }
    public String getFileSavePath() {
        return fileSavePath;
    }

    public String getHost_name() {
        return host_name;
    }

    public String getInfoUrl() {
        return infoUrl;
    }
}

