package com.yabushan.datasource.utils;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC工具类，简化JDBC编程
 */
public class DBUtil {
    // 工具类中的构造方法都是私有化的，所有工具类中的方法都是静态公共的
    // 不需要new对象
    private DBUtil() {
    }

    // 静态方法直接通过类名调用
    public static Connection getConnection(String driverClass,String url,String user,String pwd) {
        Connection conn = null;
        try {
            //1、加载驱动
            Class.forName(driverClass);
            //2、获取与数据库的连接
            conn = DriverManager.getConnection(url, user, pwd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;       // 返回一个数据库操作对象
    }

    public static void close(Connection conn, Statement statement,ResultSet resultSet) {
        //释放资源

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   
}
