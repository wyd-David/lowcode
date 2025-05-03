package com.yabushan.ai.utils;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WordsResultVo {
    private List<Map> words_result;
    private String log_id;
    private Integer words_result_num;
    
}
