package com.luos.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据连接与关闭
 * Created by luos on 2016/10/29.
 */
public class DbUtil {

    /**
     * 连接数据库
     *
     * @return
     * @throws Exception
     */
    public static Connection getConn() throws Exception {
        String dbUrl = PropertiesUtil.getValue("dbUrl");
        String dbUserName = PropertiesUtil.getValue("dbUserName");
        String dbPassword = PropertiesUtil.getValue("dbPassword");
        String jdbcName = PropertiesUtil.getValue("jdbcName");

        Class.forName(jdbcName);
        Connection conn = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        return conn;
    }

    /**
     * 关闭数据库
     *
     * @param conn
     * @throws Exception
     */
    public static void closeConn(Connection conn) throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

//
//	public static void main(String args[]) {
//		DbUtil dbUtil = new DbUtil();
//		try {
//			Connection conn = dbUtil.getConn();
//			System.out.println("连接成功！"+conn.toString());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("连接失败！");
//		}
//	}

}
