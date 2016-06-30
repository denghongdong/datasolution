package com.jdjr.datasolution.dao;

import com.jdjr.datasolution.domain.DPShopDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiaodengliang on 2016/5/18.
 */
@Repository
public interface DpDAO {

    //查询总记录数
    @Select("SELECT COUNT(*) FROM jdjr_data.dp_food_shops")
    public int getRecord();

    //插入数据
    @Insert("INSERT INTO jdjr_data.dp_food_shops (city_enName,cityId,city_name,brand_name,shopId," +
            "shop_name,shop_address,comments_num,taste,surroundings,service,per_consumption," +
            "shop_url,file_path,operate_time,opening_date)" +
            "VALUES(#{city_enName}, #{cityId}, #{city_name}, #{brand_name}, #{shopId}, #{shopId}," +
            " #{shop_name}, #{shop_address},#{comments_num},#{taste},#{surroundings},#{service}," +
            "#{per_consumption},#{shop_url},#{file_path},#{operate_time},#{opening_date}")
    public int saveData(DPShopDO dpShopDO);


    //查询数据
    @Select("SELECT * FROM jdjr_data.dp_food_shops WHERE (login=#{login} or email=#{login})")
    public List<DPShopDO> findShopById(@Param(value = "id") int id);

    // 插入月平均评论数
    @Insert("INSERT INTO jdjr_data.dp_food_shops (monthComment,login,email,password,sugar,status,addtime,modtime)" +
            "VALUES(#{name}, #{login}, #{email}, #{password}, #{sugar}, '0', unix_timestamp(), unix_timestamp())")
    public void insertMonthComment(String monthComment, int id);


     //插入经度和纬度
    @Update("UPDATE jdjr_data.dp_food_shops SET longitude = #{longitude}, latitude = #{latitude} WHERE id = #{id}")
    public void insertLngAndLat(@Param(value = "longitude") String longitude,
                                @Param(value = "latitude") String latitude,
                                @Param(value = "id") int id);
}