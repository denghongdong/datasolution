package com.jdjr.datasolution.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtil {
	
	private static ComboPooledDataSource  dataSource = new ComboPooledDataSource("jdjr");
	/**
	 * 获得数据源
	 * @return
	 */
	public static DataSource getDataSource(){
		return dataSource;
		
	}
	/**
	 * 获得链接
	 * @return
	 */
	public static Connection getConnection(){
		try {
			return getDataSource().getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}	
	}

	/**
	 * 关闭数据库
	 */
	public static void closeConnection(){
		dataSource.close();
	}
}
