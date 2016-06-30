package com.jdjr.datasolution.param;

/**
 * Created by xiaodengliang on 2016/5/20.
 */
public class StringInfoParam {
    private String comments_num;//评论数
    private String per_consumption;//平均每人消费额
    private String taste;//口味
    private String surroundings;//环境
    private String service;//服务

    private String total_score;//总分
    private String monthly_comments_num;//每月评论数


    public String getComments_num() {
        return comments_num;
    }

    public void setComments_num(String comments_num) {
        this.comments_num = comments_num;
    }

    public String getPer_consumption() {
        return per_consumption;
    }

    public void setPer_consumption(String per_consumption) {
        this.per_consumption = per_consumption;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getSurroundings() {
        return surroundings;
    }

    public void setSurroundings(String surroundings) {
        this.surroundings = surroundings;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
