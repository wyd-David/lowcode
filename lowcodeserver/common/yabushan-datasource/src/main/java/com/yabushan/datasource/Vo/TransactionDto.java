package com.yabushan.datasource.Vo;

import lombok.Data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@Data
public class TransactionDto {
    
    private Integer sort;//执行顺序
    
    private String apiCode;//api接口编码
    
    private Map params; //参数map
    
    
    
    //以下2个参数非接口传入
    private String defitionSql;//sql语句
    private String driverType;//数据库类型
    private String driverAndInstanceId;//驱动类型和实例ID
    
}
