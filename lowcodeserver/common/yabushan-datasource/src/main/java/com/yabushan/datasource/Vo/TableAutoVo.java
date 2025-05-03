package com.yabushan.datasource.Vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class TableAutoVo {
    String resourceId;
    String param;
    int pageNum;
    int pageSize;
}
