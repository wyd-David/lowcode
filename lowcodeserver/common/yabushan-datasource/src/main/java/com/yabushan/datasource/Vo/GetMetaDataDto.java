package com.yabushan.datasource.Vo;

import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;
@Data
@ApiOperation("元数据实体类")
public class GetMetaDataDto extends BaseEntity {
    @ApiModelProperty("数据源ID")
    String datasourceId;
    @ApiModelProperty("数据库表，不为空时，则增量抓取，为空时抓取全量")
    List<String> tableNameList;
    @ApiModelProperty("驱动类型：mysql、oracle、sqlserver等其他")
    String driverClass;
    @ApiModelProperty("表的资源ID")
    String resourceId;
    @ApiModelProperty("查询表信息时传入的表名称")
    String tableName;
}
