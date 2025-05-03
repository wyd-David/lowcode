package com.yabushan.datasource.Vo;

import lombok.Data;

@Data
public class TableHeader {
    
    private String key="1";
    private String label;
    private String prop;
    private String width="160";
    private String headerAlign ="center";
    private String align ="center";
    private Boolean scope=false;
    private Boolean sortable = true;
    private Boolean propChildren =null;
            
}
