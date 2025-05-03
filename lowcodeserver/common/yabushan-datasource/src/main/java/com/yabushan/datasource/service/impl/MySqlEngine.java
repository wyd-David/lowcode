package com.yabushan.datasource.service.impl;

import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.uuid.IdUtils;
import com.yabushan.datasource.domain.DataStructuredColumn;
import com.yabushan.datasource.domain.DigDataResource;
import com.yabushan.datasource.service.DBSqlEngine;
import com.yabushan.datasource.service.DynamicDataSourceFactory;
import com.yabushan.datasource.service.IDataStructuredColumnService;
import com.yabushan.datasource.service.IDigDataResourceService;
import com.yabushan.datasource.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

/**
 * mysql执行引擎
 */
@Service
@EnableTransactionManagement
public class MySqlEngine extends DynamicDataSourceFactory implements  DBSqlEngine{
    
    @Value("${autodatasource.mysqldatasourceId}")
   String MySqlDatasourceId;


    @Autowired
    private IDigDataResourceService dataResourceService;
    @Autowired
    private IDataStructuredColumnService dataStructuredColumnService;
   


    
    @Override
    public void getDatasourceAndFiled(Connection conn, String sql,String schema,String datasourceId) throws Exception {
        Statement stat=null;
        Statement stat2=null;
        ResultSet resultSet=null;
        ResultSet filedSet=null;
        try{
            stat = conn.createStatement();
            stat2 = conn.createStatement();
            resultSet = stat2.executeQuery(sql);
            while (resultSet.next()) {
                DigDataResource digDataResource =null; 
                String TABLE_NAME = resultSet.getString("TABLE_NAME");
                String TABLE_COMMENT = resultSet.getString("TABLE_COMMENT");
                
                //校验表是否存在
                DigDataResource digDataResource1 = new DigDataResource();
                digDataResource1.setDatasourceId(datasourceId);
                digDataResource1.setResourceCode(TABLE_NAME);
                List<DigDataResource> digDataResources = dataResourceService.selectDataResourceList(digDataResource1);
                if(digDataResources.size()>0){
                    digDataResource = digDataResources.get(0);
                    digDataResource.setResourceName(TABLE_COMMENT==""?TABLE_NAME:TABLE_COMMENT);
                    dataResourceService.updateDataResource(digDataResource);
                }else{
                    digDataResource= new DigDataResource();
                    digDataResource.setResourceId(IdUtils.simpleUUID());
                    digDataResource.setResourceCode(TABLE_NAME);
                    digDataResource.setResourceName(TABLE_COMMENT==""?TABLE_NAME:TABLE_COMMENT);
                    digDataResource.setResourceTableName(TABLE_NAME);
                    digDataResource.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUSERID());
                    digDataResource.setCreateTime(new Date());
                    digDataResource.setCreateUserName(SecurityUtils.getUsername());
                    digDataResource.setResourceType("table");
                    digDataResource.setDatasourceId(datasourceId);
                    dataResourceService.insertDataResource(digDataResource);
                }

                //获取字段信息
                sql = "SELECT F.COLUMN_NAME,F.IS_NULLABLE,F.DATA_TYPE,F.CHARACTER_MAXIMUM_LENGTH,F.NUMERIC_PRECISION,F.COLUMN_TYPE,F.COLUMN_KEY,F.EXTRA,F.COLUMN_COMMENT,F.COLUMN_DEFAULT FROM INFORMATION_SCHEMA.COLUMNS F ";
                StringBuffer fieldBuf = new StringBuffer();
                fieldBuf.append("where F.TABLE_NAME='").append(TABLE_NAME).append("'");
                fieldBuf.append("and F.TABLE_SCHEMA='").append(schema).append("'");
                sql+= fieldBuf.toString();
                filedSet = stat.executeQuery(sql);
                while (filedSet.next()) {
                    
                    //校验字段是否存在
                    DataStructuredColumn dataStructuredColumn = new DataStructuredColumn();
                    dataStructuredColumn.setResourceId(digDataResource.getResourceId());
                    
                    String COLUMN_NAME = filedSet.getString("COLUMN_NAME");
                    dataStructuredColumn.setColumnCode(COLUMN_NAME);

                    List<DataStructuredColumn> dataStructuredColumns = dataStructuredColumnService.selectDataStructuredColumnList(dataStructuredColumn);
                    //字段英文名称
                    dataStructuredColumn.setName(COLUMN_NAME);
                    //是否为空
                    String IS_NULLABLE = filedSet.getString("IS_NULLABLE");
                    if(!"YES".equals(IS_NULLABLE)){
                        dataStructuredColumn.setIsNullable("0");
                    }


                    //数据类型 varchar
                    String DATA_TYPE = filedSet.getString("DATA_TYPE");
                    dataStructuredColumn.setTypeName(DATA_TYPE);

                    //最大长度 CHARACTER_MAXIMUM_LENGTH
                    String CHARACTER_MAXIMUM_LENGTH = filedSet.getString("CHARACTER_MAXIMUM_LENGTH");
                    if(CHARACTER_MAXIMUM_LENGTH!=null){
                        dataStructuredColumn.setLength(Long.valueOf(CHARACTER_MAXIMUM_LENGTH));
                    }
                    //精度
                    String NUMERIC_PRECISION = filedSet.getString("NUMERIC_PRECISION");
                    dataStructuredColumn.setPrecision(NUMERIC_PRECISION);

                    //是否主键
                    String COLUMN_KEY = filedSet.getString("COLUMN_KEY");
                    if(StringUtils.isNotNull(COLUMN_KEY) && "PRI".equals(COLUMN_KEY)){
                        dataStructuredColumn.setIsPrimaryKey("1");
                    }


                    //默认值
                    String COLUMN_DEFAULT = filedSet.getString("COLUMN_DEFAULT");
                    dataStructuredColumn.setDefaultValue(COLUMN_DEFAULT);

                    //中文名
                    String COLUMN_COMMENT = filedSet.getString("COLUMN_COMMENT");
                    dataStructuredColumn.setDisplayName(StringUtils.isEmpty(COLUMN_COMMENT)?COLUMN_NAME:COLUMN_COMMENT);

                    String COLUMN_TYPE = filedSet.getString("COLUMN_TYPE");
                    dataStructuredColumn.setColumnType(COLUMN_TYPE);
                    //类型分类NUMBER、STRING、DATE、BINARY
                    if(COLUMN_TYPE.toLowerCase().contains("var") || COLUMN_TYPE.toLowerCase().contains("text")){
                        dataStructuredColumn.setTypeClassify("STRING");
                      //  dataStructuredColumn.setLength(null);

                    }else if(COLUMN_TYPE.toLowerCase().contains("int") || COLUMN_TYPE.toLowerCase().contains("long")
                            || COLUMN_TYPE.toLowerCase().contains("float") || COLUMN_TYPE.toLowerCase().contains("double")
                    ){
                        dataStructuredColumn.setTypeClassify("NUMBER");
                    }else if(COLUMN_TYPE.toLowerCase().contains("date")){
                        dataStructuredColumn.setTypeClassify("DATE");
                    }else{
                        dataStructuredColumn.setTypeClassify("BINARY");
                    }
                    dataStructuredColumn.setAppId(digDataResource.getAppId());
                    dataStructuredColumn.setUpdateBy(SecurityUtils.getUsername());
                    dataStructuredColumn.setUpdateTime(new Date());
                    if(dataStructuredColumns!=null && dataStructuredColumns.size()>0){
                        //修改
                        dataStructuredColumn.setColumnId(dataStructuredColumns.get(0).getColumnId());
                        dataStructuredColumn.setCreateTime(dataStructuredColumns.get(0).getCreateTime());
                        dataStructuredColumn.setCreateBy(dataStructuredColumns.get(0).getCreateBy());
                        //更新字段信息
                        dataStructuredColumnService.updateDataStructuredColumn(dataStructuredColumn);
                    }else{
                        //新增
                        dataStructuredColumn.setCreateBy(SecurityUtils.getUsername());
                        dataStructuredColumn.setCreateTime(new Date());
                        dataStructuredColumn.setResourceId(digDataResource.getResourceId());
                        dataStructuredColumn.setColumnId(IdUtils.simpleUUID());
                        //保存字段信息
                        dataStructuredColumnService.insertDataStructuredColumn(dataStructuredColumn);
                    }
                }

            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            DBUtil.close(conn,stat,resultSet);
            DBUtil.close(conn,stat2,filedSet);
        }


    }



  
}
