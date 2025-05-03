package com.yabushan.ai.utils;

import com.alibaba.fastjson.JSONObject;

public class Test {

    public static void main(String[] args) {
        WordsResultVo wordsResultVo = new WordsResultVo();
        String json = "{\n" +
                "  \"words_result\": [\n" +
                "    {\"words\": \"中华人民共和国\"},\n" +
                "    {\"words\": \"居民身份证\"},\n" +
                "    {\"words\": \"签发机关南雄市公安局\"},\n" +
                "    {\"words\": \"有效期限\"},\n" +
                "    {\"words\": \"2021.11.29-2041.11.29\"},\n" +
                "    {\"words\": \"公章\"},\n" +
                "    {\"words\": \"姓名沈学发\"},\n" +
                "    {\"words\": \"性别\"},\n" +
                "    {\"words\": \"男\"},\n" +
                "    {\"words\": \"民族汉\"},\n" +
                "    {\"words\": \"甜\"},\n" +
                "    {\"words\": \"出生1992年10月12日\"},\n" +
                "    {\"words\": \"住址广东省南雄市黄坑镇园岭\"},\n" +
                "    {\"words\": \"村委会杜禾999号\"},\n" +
                "    {\"words\": \"中H\"},\n" +
                "    {\"words\": \"公民身份号码440223199210123710\"}\n" +
                "  ],\n" +
                "  \"log_id\": 1713402168306244682,\n" +
                "  \"words_result_num\": 16\n" +
                "}";

        WordsResultVo wordsResultVo1 = JSONObject.parseObject(json, WordsResultVo.class);
        System.out.println(wordsResultVo1.toString());

    }
}
