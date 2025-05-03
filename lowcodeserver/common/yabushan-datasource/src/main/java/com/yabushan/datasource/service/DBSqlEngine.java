package com.yabushan.datasource.service;


import java.sql.*;

public interface DBSqlEngine {

    /**
     * 抓取元数据 插入生成库表信息
     * @param conn 
     * @param sql
     * @param schema 数据库schema
     * @param datasourceId 
     * @throws Exception
     */
    public void getDatasourceAndFiled(Connection conn, String sql,String schema,String datasourceId) throws Exception;



}
