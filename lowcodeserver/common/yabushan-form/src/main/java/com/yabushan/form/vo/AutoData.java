package com.yabushan.form.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AutoData {
    private List<Map<String ,Object>> rows;
    private Object total;

}
