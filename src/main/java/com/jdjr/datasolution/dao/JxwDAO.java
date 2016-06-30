package com.jdjr.datasolution.dao;

import com.jdjr.datasolution.domain.JXWGoodsDO;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * Created by xiaodengliang on 2016/6/18.
 */
@Repository
public interface JxwDAO {

    //插入数据
    @Insert("INSERT INTO jdjr_data.jxw_goods (brand_name,goods_id,goods_name,total_sales,score," +
            "price,place_of_origin,net_quantity,address,degree,spec,box_capacity," +
            "flavor,tatal_comments,goods_url,file_path,operate_time)" +
            "VALUES(#{brand_name}, #{goods_id}, #{goods_name}, #{total_sales}, #{score}, #{price}," +
            " #{place_of_origin}, #{net_quantity},#{address},#{degree},#{spec},#{box_capacity}," +
            "#{flavor},#{tatal_comments},#{goods_url},#{file_path},#{operate_time}")
    public int saveData(JXWGoodsDO jxwGoodsDO);

}