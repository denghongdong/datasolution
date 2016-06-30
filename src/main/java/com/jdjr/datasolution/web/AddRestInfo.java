package com.jdjr.datasolution.web;

import com.jdjr.datasolution.domain.DPShopDO;
import com.jdjr.datasolution.service.ISaveDpService;
import com.jdjr.datasolution.service.impl.SaveDpServiceImpl;
import com.jdjr.datasolution.util.DateUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaodengliang on 2016/5/27.
 * 功能：添加月平均评论数、经纬度
 */
public class AddRestInfo {

    public static ISaveDpService saveDataService = new SaveDpServiceImpl();
    public static DPShopDO dpShopDO = new DPShopDO();

    public static void main(String[] args) throws Exception {
        List<DPShopDO> dpShopDO;
        int totalRecords = saveDataService.getRecord();
        for (int i = 1; i <= totalRecords; i++) {
            dpShopDO = saveDataService.findShopById(i);
            addLatAndLgn(dpShopDO, i);
        }
    }


    /**
     * 插入月评论数
     * 按照  总评论数/总月数  计算
     * 取两位有效数字
     */
    public static void addMonthComments(List<DPShopDO> dpShopDO, int id) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int nowMonth = DateUtil.getMonth(date) + 1;
        int nowYear = DateUtil.getYear(date);
        int dbMonth, dbYear;
        float commentNum, totalMonth;
        String monthComment;
        String dateString;
        dateString = dpShopDO.get(0).getOpening_date();//获取开店日期，为字符串类型
        dbYear = Integer.parseInt(dateString.substring(0, dateString.indexOf("/")));//获取年
        dbMonth = Integer.parseInt(dateString.substring(dateString.indexOf("/") + 1, dateString.lastIndexOf("/")));
        totalMonth = (nowYear - dbYear) * 12 + (nowMonth - dbMonth);
        commentNum = Integer.parseInt(dpShopDO.get(0).getComments_num());
        monthComment = decimalFormat.format(commentNum / totalMonth);
        saveDataService.addMonthComment(monthComment, id);
        System.out.println("已经插入第" + id + "个数据");
    }


    /**
     * 插入经度和纬度
     * 根据百度API获得经纬度
     *
     * @param dpShopDO
     * @throws Exception
     */
    public static void addLatAndLgn(List<DPShopDO> dpShopDO, int id) throws Exception {
        String lng = null;//经度
        String lat = null;//纬度
        String address = null;
        String addr = dpShopDO.get(0).getCity_name() + dpShopDO.get(0).getShop_address();
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");//编码
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String key = "f247cdb592eb43ebac6ccd27f796e2d2";
        String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key);
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            httpsConn = (URLConnection) myURL.openConnection();//不使用代理
            if (httpsConn != null) {
                insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String data = null;
                int count = 1;
                while ((data = br.readLine()) != null) {
                    if (count == 5) {
                        lng = (String) data.subSequence(data.indexOf(":") + 1, data.indexOf(","));//经度
                        AddRestInfo.dpShopDO.setLongitude(lng);
                        count++;
                    } else if (count == 6) {
                        lat = data.substring(data.indexOf(":") + 1);//纬度
                        AddRestInfo.dpShopDO.setLatitude(lat);
                        count++;
                    } else {
                        count++;
                    }
                }
                saveDataService.addLngAndLat(AddRestInfo.dpShopDO, id);
                System.out.println("已经插入第" + id + "个数据");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (insr != null) {
                insr.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }
}
