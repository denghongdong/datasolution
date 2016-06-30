package com.jdjr.datasolution.common;

import com.jdjr.datasolution.util.JsonUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wangbo on 15/12/31.
 */
public class JsonTypeHandler implements TypeHandler<Object> {
    private static final char SEPARATOR = ';';

    public JsonTypeHandler() {
    }

    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if(parameter == null) {
            ps.setString(i, (String)null);
        } else {
            String json = JsonUtil.toJson(parameter);
            json = json + ';' + parameter.getClass().getName();
            ps.setString(i, json);
        }
    }

    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return this.jsonToObject(json);
    }

    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return this.jsonToObject(json);
    }

    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return this.jsonToObject(json);
    }

    private Object jsonToObject(String json) {
        if(json != null && !json.equals("")) {
            int index = json.lastIndexOf(59);
            if(index < 0) {
                return null;
            } else {
                String className = json.substring(index + 1, json.length());
                json = json.substring(0, index);
                Class classType = null;

                try {
                    classType = Class.forName(className);
                } catch (ClassNotFoundException var6) {
                    throw new RuntimeException("反序列化json为对象时找不到指定的类:" + className, var6);
                }

                Object obj = JsonUtil.fromJson(json, classType);
                return obj;
            }
        } else {
            return null;
        }
    }
}
