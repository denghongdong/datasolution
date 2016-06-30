package com.jdjr.datasolution.domain;

/**
 * Created by xiaodengliang on 2016/5/18.
 */
public class DPShopDO {

    private String city_enName;//城市英文名
    private String cityId;//城市id
    private String city_name;//城市名
    private String brand_name;//品牌名
    private String shopId;//店铺号
    private String shop_name;//店铺名
    private String shop_address;//地址

    private String longitude;//经度
    private String latitude;//纬度
    private String opening_date;//开店日期
    private String comments_num;//评论数
    private String monthly_comments_num;//每月评论数

    private String total_score;//总分
    private String taste;//口味
    private String surroundings;//环境
    private String service;//服务
    private String per_consumption;//平均每人消费额
    private String operate_time;//操作时间
    private String shop_url;//店铺url
    private String file_path;//文件路径

    public String getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(String surroundings) {
        this.surroundings = surroundings;
    }

    public String getCity_enName() {
        return city_enName;
    }

    public void setCity_enName(String city_enName) {
        this.city_enName = city_enName;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }


    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getOpening_date() {
        return opening_date;
    }

    public void setOpening_date(String opening_date) {
        this.opening_date = opening_date;
    }

    public String getComments_num() {
        return comments_num;
    }

    public void setComments_num(String comments_num) {
        this.comments_num = comments_num;
    }

    public String getMonthly_comments_num() {
        return monthly_comments_num;
    }

    public void setMonthly_comments_num(String monthly_comments_num) {
        this.monthly_comments_num = monthly_comments_num;
    }

    public String getTotal_score() {
        return total_score;
    }

    public void setTotal_score(String total_score) {
        this.total_score = total_score;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPer_consumption() {
        return per_consumption;
    }

    public void setPer_consumption(String per_consumption) {
        this.per_consumption = per_consumption;
    }

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(String operate_time) {
        this.operate_time = operate_time;
    }
}
