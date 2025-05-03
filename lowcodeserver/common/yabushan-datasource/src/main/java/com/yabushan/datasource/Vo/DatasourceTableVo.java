package com.yabushan.datasource.Vo;

import com.yabushan.datasource.utils.pages.PageFinderDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DatasourceTableVo {
    
    private List<TableHeader> tableHeaderList;
    
    private PageFinderDTO<Map<String, Object>> data;
}
