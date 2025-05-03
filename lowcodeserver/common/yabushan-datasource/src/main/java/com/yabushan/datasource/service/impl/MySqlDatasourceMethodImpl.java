package com.yabushan.datasource.service.impl;


import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.datasource.Vo.Constants;
import com.yabushan.datasource.domain.*;
import com.yabushan.datasource.service.*;
import com.yabushan.datasource.utils.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

@Service
public class MySqlDatasourceMethodImpl implements DatasourceMethodFactory {
    
    @Autowired
    private MySqlEngine mySqlEngine;
    @Autowired
    private IAppUnitInstanceService appUnitInstanceService;
    @Autowired
    private IAppAccountService appAccountService;
    @Autowired
    private IAppConnectorService appConnectorService;
    @Autowired
    private IAppConnectorAttrService appConnectorAttrService;
    @Autowired
    private IDigDataResourceService dataResourceService;
    @Autowired
    private IDataStructuredColumnService dataStructuredColumnService;
    @Autowired
    private IMetadataDatasourceTypeParamService metadataDatasourceTypeParamService;

    /**
     * 校验数据源是否正常 
     * @param driverClass
     * @param ip
     * @param port
     * @param database
     * @param userName
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public boolean validataDatasource(String driverClass, String ip,String port,String database, String userName, String password) throws Exception {
        Connection conn= null;
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        java.lang.String url = "jdbc:mysql://"+ip+":"+port+"/"+database+"?serverTimezone=Asia/Shanghai";
        conn = DriverManager.getConnection(url, userName, password);
        boolean execute = mySqlEngine.execute(conn, "select 1 ");
        return  execute;
    }


    /**
     * 注册一个mysql数据源 
     * @param userName
     * @param passWord
     * @param instanceName
     * @param versionCode
     * @param IP
     * @param PORT
     * @param instanceDisplayName
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean registerDatasource( String userName, String passWord,String instanceName,String versionCode,String IP,String PORT,String instanceDisplayName,String schema, String instanceId) throws Exception {
       //1.对账号进行加密
        String encPassword = AESUtil.aesEncrypt(passWord);
        //2.保存数据库实例
        AppUnitInstance appUnitInstance = new AppUnitInstance();
        String accountId = StringUtils.getUUID();
        String appId = StringUtils.getUUID();
        if(StringUtils.isNull(instanceId)){
            instanceId = StringUtils.getUUID();
        }
        appUnitInstance.setAppId(appId);
        appUnitInstance.setInstanceId(instanceId);
        appUnitInstance.setPath(instanceId);
        appUnitInstance.setQuotaUnitCode(userName);
        appUnitInstance.setInstanceName(instanceName);
        appUnitInstance.setInstanceCode(schema);
        appUnitInstance.setInstanceDisplayName(instanceDisplayName);
        appUnitInstance.setAccountId(accountId);
        appUnitInstance.setVersionCode(versionCode);
        appUnitInstance.setCreateTime(new Date());
        appUnitInstance.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUSERID());
        appUnitInstance.setCreateUserName(SecurityUtils.getNickName());
        appUnitInstance.setOwnerId(SecurityUtils.getLoginUser().getUser().getUSERID());
        appUnitInstance.setOwnerName(SecurityUtils.getUsername());
        appUnitInstance.setAppClassCode(Constants.MYSQL);
        appUnitInstanceService.insertAppUnitInstance(appUnitInstance);
        //3.保存数据库账号密码
         AppAccount appAccount= new AppAccount();
         appAccount.setAccountId(accountId);
         appAccount.setAppId(appId);
         appAccount.setUsername(userName);
         appAccount.setPassword(encPassword);
         appAccount.setOwnerType("User");
         appAccount.setOwnerId(SecurityUtils.getLoginUser().getUser().getUSERID());
         appAccount.setOwnerName(SecurityUtils.getUsername());
         appAccount.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUSERID());
         appAccount.setCreateTime(new Date());
         appAccountService.insertAppAccount(appAccount);
         
         //4.保存连接器信息
        String connectorId = StringUtils.getUUID();
        AppConnector appConnector = new AppConnector();
        appConnector.setAppId(appId);
        appConnector.setConnectorId(connectorId);
        appConnector.setConnectorName(instanceName);
        appConnector.setIsDeleted(0L);
        appConnectorService.insertAppConnector(appConnector);
        //5.保存参数信息
        List<AppConnectorAttr> list = new ArrayList<>();
        //ip
        AppConnectorAttr appConnectorAttr = new AppConnectorAttr();
        appConnectorAttr.setAttrId(StringUtils.getUUID());
        appConnectorAttr.setConnectorId(connectorId);
        appConnectorAttr.setAttrCode("ip");
        appConnectorAttr.setAttrValue(IP);
        list.add(appConnectorAttr);
        //port
        AppConnectorAttr appConnectorAttr1 = new AppConnectorAttr();
        appConnectorAttr1.setAttrId(StringUtils.getUUID());
        appConnectorAttr1.setConnectorId(connectorId);
        appConnectorAttr1.setAttrCode("port");
        appConnectorAttr1.setAttrValue(PORT);
        list.add(appConnectorAttr1);
        //version
        AppConnectorAttr version = new AppConnectorAttr();
        version.setAttrId(StringUtils.getUUID());
        version.setConnectorId(connectorId);
        version.setAttrCode("version");
        version.setAttrValue(versionCode);
        list.add(version);

        
        
        appConnectorAttrService.bathInsertAppConnectorAttr(list);
        return true;
    }

    /**
     * 获取数据库的元数据 
     * @param datasourceId
     * @param tableNameList
     * @return
     * @throws Exception
     */
    @Override
    public boolean getDataSourceMetaData(String datasourceId,List<String> tableNameList) throws Exception {
        
        //1.根据数据源ID 获取到数据源
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(datasourceId);
        
        //2.获取到数据源
        DataSource oneDatasourceFromPool = getOneDatasourceFromPool(datasourceId);
        
        //3.获取到所有表
        String userName = appUnitInstance.getInstanceName();
        Connection conn= oneDatasourceFromPool.getConnection();
        String sql = "SELECT t.TABLE_NAME ,t.TABLE_COMMENT FROM information_schema.tables t WHERE t.TABLE_SCHEMA='"+appUnitInstance.getInstanceName()+"'";
        StringBuffer buffer = new StringBuffer();
        if(tableNameList!=null && tableNameList.size()>0){
            buffer.append(" and TABLE_NAME in('");
            String result = String.join("','", tableNameList);
            buffer.append(result);
            buffer.append("')");
        }
        sql+=buffer.toString();
        
        //4.获取到的数据库表 //遍历库表信息，保存库表
        mySqlEngine.getDatasourceAndFiled(conn, sql,userName,datasourceId);
        return true;
    }

  

    @Override
    public List<DigDataResource> queryDataTable(String  unitInstanceId,String tableName) {
        DigDataResource digDataResource = new DigDataResource();
        digDataResource.setDatasourceId(unitInstanceId);
        digDataResource.setEnableStatus("1");
        digDataResource.setResourceTableName(tableName);
        digDataResource.setResourceType("table");
        List<DigDataResource> digDataResources = dataResourceService.selectDataResourceList(digDataResource);
        return digDataResources;
    }

    @Override
    public List<DataStructuredColumn> queryDataStructuredColumn(String  resourceId) {
        DataStructuredColumn dataStructuredColumn = new DataStructuredColumn();
        dataStructuredColumn.setResourceId(resourceId);
        List<DataStructuredColumn> dataStructuredColumns = dataStructuredColumnService.selectDataStructuredColumnList(dataStructuredColumn);
        return dataStructuredColumns;
    }

    @Override
    public DataSource getOneDatasourceFromPool(String datasourceId) throws Exception {

        DataSource dataSource = DynamicDataSourceFactory.getDataSource(datasourceId);
        if(dataSource!=null){
            return dataSource;
        }
        
        //1.根据数据源ID 获取到数据源
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(datasourceId);

        AppAccount appAccount = new AppAccount();
        appAccount.setAppId(appUnitInstance.getAppId());
        appAccount = appAccountService.selectAppAccountList(appAccount).get(0);

        AppConnector appConnector = new AppConnector();
        appConnector.setAppId(appUnitInstance.getAppId());
        appConnector = appConnectorService.selectAppConnectorList(appConnector).get(0);

        AppConnectorAttr appConnectorAttr = new AppConnectorAttr();
        appConnectorAttr.setConnectorId(appConnector.getConnectorId());
        List<AppConnectorAttr> appConnectorAttrList = appConnectorAttrService.selectAppConnectorAttrList(appConnectorAttr);
        String ip = null;
        String port = null;
        for (AppConnectorAttr connectorAttr : appConnectorAttrList) {
            if("ip".equals(connectorAttr.getAttrCode())){
                ip=connectorAttr.getAttrValue();
            }else if("port".equals(connectorAttr.getAttrCode())){
                port=connectorAttr.getAttrValue();
            }

        }
      
      //  DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        java.lang.String url = "jdbc:mysql://"+ip+":"+port+"/"+appUnitInstance.getInstanceName()+"?serverTimezone=Asia/Shanghai";
        String userName = appAccount.getUsername();
        String password = appAccount.getPassword();
        password= AESUtil.aesDecrypt(password) ;

        MetadataDatasourceTypeParam metadataDatasourceTypeParam = new MetadataDatasourceTypeParam();
        metadataDatasourceTypeParam.setDbTypeId("mysql");
        List<MetadataDatasourceTypeParam> metadataDatasourceTypeParams = metadataDatasourceTypeParamService.selectMetadataDatasourceTypeParamList(metadataDatasourceTypeParam);
     
        DataSource OneDatasource = MySqlEngine.createAndGetOneDatasource(datasourceId, url, userName, password, metadataDatasourceTypeParams);
        return OneDatasource;

    }
    
    
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Hello", "World", "!");
        String result = String.join("','", list);
        System.out.println(result);
    }
}
