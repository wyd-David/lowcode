package com.yabushan.datasource.service;

import com.yabushan.datasource.domain.DataStructuredColumn;
import com.yabushan.datasource.domain.DigDataResource;

import javax.sql.DataSource;
import java.util.List;

public interface DatasourceMethodFactory {

    /**
     * 验证数据源可用性
     * @param driverClass
     * @param ip
     * @param port
     * @param database
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    boolean validataDatasource(String driverClass, String ip,String port,String database, String userName, String password) throws Exception;

    /**
     * 注册数据源
     * @param userName
     * @param passWord
     * @param instanceName
     * @param versionCode
     * @param IP
     * @param PORT
     * @param instanceDisplyName
     * @return
     * @throws Exception
     */
    boolean registerDatasource(String userName,String passWord,String instanceName,String versionCode,String IP,String PORT,String instanceDisplyName,String schema, String instanceId) throws Exception;

    /**
     * 获取元数据
     * @param datasourceId
     * @return
     */
    boolean getDataSourceMetaData(String datasourceId,List<String> tableNameList) throws Exception;

    /**
     * 查询已抓取到的数据表信息
     * @param unitInstanceId
     * @return
     */
    List<DigDataResource> queryDataTable(String unitInstanceId,String tableName);

    /**
     * 查询数据字段
     * @param resourceId
     * @return
     */
    List<DataStructuredColumn> queryDataStructuredColumn(String  resourceId);


    /**
     * 获取一个数据源* 
     * @param datasourceId
     * @return
     * @throws Exception
     */
    DataSource getOneDatasourceFromPool(String datasourceId) throws Exception;


    


}
