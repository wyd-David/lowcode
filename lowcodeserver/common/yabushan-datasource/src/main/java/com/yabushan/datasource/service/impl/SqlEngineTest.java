package com.yabushan.datasource.service.impl;

import com.yabushan.datasource.utils.DBUtil;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class SqlEngineTest {
    
    

    /**
     * 执行mysql
     * @param url
     * @param username
     * @param password
     * @param sql
     * @return
     */
    private static String excuteMysqlState(String url,String username,String password,String sql) {
        Connection conn= null;
        Statement stat=null;
        String result=null;
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(url, username, password);

            //3、获取数据库操作对象（专门执行sql语句的对象）Statement createStatement()
            //创建一个 Statement对象，用于将SQL语句发送到数据库。  (statement是专门执行sql语句的)
            stat = conn.createStatement();
            int count = stat.executeUpdate(sql);
            if(count==1){
               //执行成功
            }else{
                result ="sql执行失败~";
            }
            
        } catch (Exception e) {
            result= e.getMessage();
    }finally {
        //6、释放资源（从小到大关闭，Java和数据库之间属于进程间的通信，开启之后一定要记得关闭）
        if(stat!=null){
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
        
       return  result;

    }



    /**
     * 执行oracle
     * @param url
     * @param username
     * @param password
     * @param sql
     * @return
     */
    private static String excuteSqlState(String driverClass ,String url,String username,String password,String sql) {
        Connection conn= null;
        Statement stat=null;
        String result=null;
        try{
           //oracle.jdbc.driver.OracleDriver
            conn = DBUtil.getConnection(driverClass,url, username, password);
            //3、获取数据库操作对象（专门执行sql语句的对象）Statement createStatement()
            //创建一个 Statement对象，用于将SQL语句发送到数据库。  (statement是专门执行sql语句的)
            stat = conn.createStatement();
            int count = stat.executeUpdate(sql);
            if(count==1){
                //执行成功
            }else{
                result ="sql执行失败~";
            }

        } catch (Exception e) {
            result= e.getMessage();
        }finally {
            //6、释放资源（从小到大关闭，Java和数据库之间属于进程间的通信，开启之后一定要记得关闭）
            DBUtil.close(conn,stat,null);
        }

        return  result;

    }
    
    
    public static void main11(String[] args) throws SQLException {
        Connection conn= null;
        Statement stat=null;

        try {
            
          

            String url = "jdbc:clickhouse://10.16.196.68:8123/default";
            String driverClass="com.clickhouse.jdbc.ClickHouseDriver";
            String username = "root";
            String password = "pass@word1";
           // conn = DriverManager.getConnection(url, username, password);
            conn = DBUtil.getConnection(driverClass,url, username, password);
            //3、获取数据库操作对象（专门执行sql语句的对象）Statement createStatement()
            //创建一个 Statement对象，用于将SQL语句发送到数据库。  (statement是专门执行sql语句的)
            stat = conn.createStatement();
            //相当于开启事务
         //   conn.setAutoCommit(false);
            //4、执行sql
            //int executeUpdate(String sql)
            //执行给定的SQL语句，这可能是 INSERT ， UPDATE ，或 DELETE语句，或者不返回任何内容，如SQL DDL语句的SQL语句。
            /*String sql = "select  1";
            stat.execute(sql);*/
            ResultSet resultSet = stat.executeQuery("SELECT name as TABLE_NAME,comment as TABLE_COMMENT  FROM system.tables  WHERE database = 'default' AND name = 'text0103zjx001' ");
            while (resultSet.next()) {
                String TABLE_NAME = resultSet.getString("TABLE_NAME");
                System.out.println(" TABLE_NAME: " + TABLE_NAME );
                String TABLE_COMMENT = resultSet.getString("TABLE_COMMENT");
                System.out.println(" TABLE_COMMENT: " + TABLE_COMMENT );
            }
           /* stat.execute("UPDATE test_api SET TEST_NAME='fage1111' WHERE TEST_ID='66'");
            if("1".equals("1")){
                throw new Exception("shoudongfanhui");
                
            }*/
            //事务提交
          //  conn.commit();

        } catch (Exception e) {
            //如果出错就回滚
           // conn.rollback();
            e.printStackTrace();
        }finally {
            //6、释放资源（从小到大关闭，Java和数据库之间属于进程间的通信，开启之后一定要记得关闭）
            if(stat!=null){
                try {
                    stat.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }


    public static void main2(String[] args) throws SQLException {
        Connection conn= null;
        Statement stat=null;

        try {
            String url = "jdbc:sqlserver://10.201.61.212:1433;databaseName=SXF";
            String driverClass="com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String username = "sa";
            String password = "Bingosoft2021";
            conn = DBUtil.getConnection(driverClass,url, username, password);
            stat = conn.createStatement();
            ResultSet resultSet = stat.executeQuery("select 1 ");
            while (resultSet.next()) {
                String TABLE_NAME = resultSet.getString("TABLE_NAME");
                System.out.println(" TABLE_NAME: " + TABLE_NAME );
                String TABLE_COMMENT = resultSet.getString("TABLE_COMMENT");
                System.out.println(" TABLE_COMMENT: " + TABLE_COMMENT );
            }

        } catch (Exception e) {
            //如果出错就回滚
            // conn.rollback();
            e.printStackTrace();
        }finally {
            //6、释放资源（从小到大关闭，Java和数据库之间属于进程间的通信，开启之后一定要记得关闭）
            if(stat!=null){
                try {
                    stat.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

    public static void main33(String[] args) {
        String url = "jdbc:sqlite:E:\\temp\\sqlitedb\\test.db"; // 数据库路径  
        Connection conn = null;
        try {
            // 加载驱动程序  
            Class.forName("org.sqlite.JDBC");
            // 建立连接  
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the database!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close(); // 关闭连接  
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    }

    public static void main(String[] args) {
            String url = "jdbc:db2://10.201.61.177:50000/sample";
            String user = "db2inst1";
            String password = "db2inst1";
            try {
                // 加载驱动程序  
                Class.forName("com.ibm.db2.jcc.DB2Driver");
                // 建立数据库连接  
                Connection connection = DriverManager.getConnection(url, user, password);
                // 执行SQL查询  
                System.out.println("查询结果：");
                String sql = "SELECT * FROM TEST.TEST1";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("COLUMN1"));
                    }
                }
                // 关闭连接  
                connection.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
    
}
