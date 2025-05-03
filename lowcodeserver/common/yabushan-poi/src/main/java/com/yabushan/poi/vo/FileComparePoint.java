package com.yabushan.poi.vo;

import lombok.Data;

import java.util.List;

@Data
public class FileComparePoint {
    List<PaperContentVo> result;
    String point;
}
