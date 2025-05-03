package com.yabushan.datasource.Vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("数据源连接对象实体类")
public class DatabaseDto {

    @ApiModelProperty("驱动类型：mysql、oracle、sqlserver等其他")
    String driverClass;
    @ApiModelProperty("IP")
    String ip;
    @ApiModelProperty("PORT")
    String port;
    @ApiModelProperty("数据库名称")
    String database;
    @ApiModelProperty("用户名")
    String userName;
    @ApiModelProperty("密码")
    String password;
    @ApiModelProperty("schema信息")
    String schema;
}
