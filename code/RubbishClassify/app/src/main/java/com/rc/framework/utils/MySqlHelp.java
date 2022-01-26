package com.rc.framework.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 自定义mysql链接辅助工具类
 */
public class MySqlHelp {

    /*
    private static String DRIVER = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://ip:3306/rubbish_classify";
    private static String USER = "rc_user";
    private static String PWD = "rc_user";
    */

    public static Connection getConn() {
        Connection connection = null;
        try {
            Class.forName(ConstUtil.DB_DRIVER);// 动态加载类
            // 尝试建立到给定数据库URL的连接，连接格式：驱动名称+ip地址+端口号+数据库名称+用户名+密码
            connection = DriverManager.getConnection(ConstUtil.DB_TX_CLOUD_URL, ConstUtil.DB_USER, ConstUtil.DB_PWD);
        } catch (Exception e) {
            System.out.println("mysql 连接创建失败");
            e.printStackTrace();
        }
        return connection;
    }

    public static void testConn() {
        Connection conn = getConn();
        String sql = "select count(1) as ct from rc_user";
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println("数据库查询结果" + rs.getInt("ct"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("关闭数据库连接");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 测试数据库连接
        System.out.println(MySqlHelp.getConn());
    }

}
