package com.yabushan.ai.domain;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.List;

@Data
public class AiResultVo {
    
    private List<Aiwords> words_result;
    private String log_id;
    private String words_result_num;
    
}
