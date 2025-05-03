package com.yabushan.web.controller.data;

import lombok.Data;

@Data
public class Extsql {

    private Integer id;//api表的主键
    private String sql;
    private String datasourceId;
    private String param;
    private String rowType;
    private String userId;
    private String apiName;

}
