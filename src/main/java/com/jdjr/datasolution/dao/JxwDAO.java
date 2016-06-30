package com.jdjr.datasolution.dao;

import com.jdjr.datasolution.domain.JXWGoodsDO;
import com.jdjr.datasolution.util.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import java.sql.SQLException;

/**
 * Created by xiaodengliang on 2016/5/18.
 */
public class JxwDAO {
    /**
     * 插入基本数据
     * @param
     * @return
     */
    public int saveData(JXWGoodsDO jxwGoodsDO) {
        try {
            QueryRunner runner = new QueryRunner(JdbcUtil.getDataSource());
            String sql = "insert into jxw_goods(brand_name,goods_id,goods_name,total_sales,score," +
                    "price,place_of_origin,net_quantity,address,degree,spec,box_capacity," +
                    "flavor,tatal_comments,goods_url,file_path,operate_time) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {jxwGoodsDO.getBrand_name(),jxwGoodsDO.getGoods_id(),jxwGoodsDO.getGoods_name(),
            jxwGoodsDO.getTotal_sales(),jxwGoodsDO.getScore(),jxwGoodsDO.getPrice(),jxwGoodsDO.getPlace_of_origin(),
            jxwGoodsDO.getNet_quantity(),jxwGoodsDO.getAddress(),jxwGoodsDO.getDegree(),jxwGoodsDO.getSpec(),
            jxwGoodsDO.getBox_capacity(),jxwGoodsDO.getFlavor(),jxwGoodsDO.getTatal_comments(),jxwGoodsDO.getGoods_url(),
            jxwGoodsDO.getFile_path(),jxwGoodsDO.getOperate_time()};
            return runner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}