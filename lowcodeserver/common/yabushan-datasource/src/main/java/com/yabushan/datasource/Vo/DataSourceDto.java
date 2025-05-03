package com.yabushan.datasource.Vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

@Data
@ApiOperation("数据源注册实体类")
public class DataSourceDto extends  DatabaseDto{

    
    @ApiModelProperty("版本信息")
    String versionCode;
    @ApiModelProperty("实例名称")
    String instanceDisplyName;
    String instanceId;
}
