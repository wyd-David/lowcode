package com.yabushan.ai.service;

import com.hankcs.hanlp.HanLP;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * *语义分词 
 */
@Service
public class YuyifenciService {


    /**
     * 提取文本关键词
     * * @param keyword
     * @return
     */
    public  List<String> extractKeyword(String keyword){
        List<String> strings1 = HanLP.extractKeyword(keyword,5);
        return strings1; 
    }

    /**
     * *提取文本摘要 
     * @param keyword
     * @return
     */
    public  List<String> extractSummary(String keyword){
        List<String> strings1 = HanLP.extractSummary(keyword,5);
        return strings1;
    }

    public  List<String> extractPhrase(String keyword){
        List<String> strings1 = HanLP.extractPhrase(keyword,5);
        return strings1;
    }


    /**
     * 获取标签
     * * @param keyword
     * @return
     */
    public List<String> getTargetMessage(String keyword) {
        List<String> value = new ArrayList<>();
        List<String> strings = this.extractKeyword(keyword);
        if(strings.size()>0){
            for (String s:strings) {
                if(s.length()>1){
                    value.add(s);
                }
            }
        }
        List<String> strings1 = this.extractPhrase(keyword);
        if(strings.size()>0){
            for (String s:strings1) {
                if(s.length()>1){
                    value.add(s);
                }
            }
        }

        return  value;
    }
}
