package com.jdjr.datasolution.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by xiaodengliang on 2016/6/3.
 * ADSL拨号上网
 * Windwos操作系统需要是GBK编码
 */
public class ConnectAdslNet {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectAdslNet.class);
    /**
     * 执行CMD命令,并返回String字符串
     */
    public static String executeCmd(String strCmd) throws Exception {
        Process p = Runtime.getRuntime().exec("cmd /c " + strCmd);
        StringBuilder sbCmd = new StringBuilder();
        //注意编码 GBK
        BufferedReader br = new BufferedReader(new InputStreamReader(p
                .getInputStream(),"GBK"));
        String line;
        while ((line = br.readLine()) != null) {
            sbCmd.append(line + "\n");
        }
        return sbCmd.toString();
    }
    /**
     * 连接ADSL
     */
    public static boolean connectAdsl(String adslTitle, String adslName, String adslPass)
            throws Exception {
        System.out.println("正在建立连接.");
        String adslCmd = "rasdial " + adslTitle + " " + adslName + " "
                + adslPass;
        String tempCmd = executeCmd(adslCmd);
    // 判断是否连接成功
        if (tempCmd.indexOf("已连接") > 0) {
            System.out.println("已成功建立连接.");
            return true;
        } else {
            System.out.println(tempCmd);
            System.out.println("建立连接失败");
            return false;
        }
    }
    /**
     * 断开ADSL
     */
    public static boolean disconnectAdsl(String adslTitle) throws Exception {
        String disconnectAdsl = "rasdial " + adslTitle + " /disconnect";
        String result = executeCmd(disconnectAdsl);
        if (result.indexOf("没有连接")!=-1){
            System.out.println(adslTitle + "连接不存在!");
            return false;
        } else {
            System.out.println("连接已断开");
            return true;
        }
    }
    /**
     * adsl重新拨号，支持失败不断重拨
     * @throws Exception
     */
    public static boolean reconnectAdsl(String adslTitle, String adslName, String adslPass){
        boolean bAdsl = false;
        try {
            disconnectAdsl(adslTitle);
            Thread.sleep(3000);
            bAdsl = connectAdsl(adslTitle,adslName,adslPass);
            Thread.sleep(3000);
            int i = 0;
            while (!bAdsl){
                disconnectAdsl(adslTitle);
                Thread.sleep(3000);
                bAdsl = connectAdsl(adslTitle,adslName,adslPass);
                Thread.sleep(3000);
                if(i>5){
                    break;
                }
            }
        }catch(Exception e){
            LOGGER.error("ADSL拨号异常：", e);
        }
        return bAdsl;
    }
}
