package com.yabushan.poi.vo;

import lombok.Data;

import java.util.List;

@Data
public class FileCompareResult {

    private String sourceFilePath; //源文件路径
    private String targetFilePath;//比对目标文件路径
    private FileComparePoint fileComparePoint;//相似度
    private String similarityRatio;//相似度段落集合

}
