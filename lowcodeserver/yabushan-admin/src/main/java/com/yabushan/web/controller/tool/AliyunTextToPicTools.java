package com.yabushan.web.controller.tool;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AliyunTextToPicTools {

    String json = "{\"request_id\":\"d3a4e35c-816b-91e3-95bf-967613180912\",\"output\":{\"task_id\":\"e1d1562c-8139-4426-8bb3-be2f37738d21\",\"task_status\":\"SUCCEEDED\",\"results\":[{\"orig_prompt\":\"商业摄影风格，18岁中国女孩身着淡雅蓝色旗袍，立领盘扣，袖口绣着金色祥云暗纹。她圆润脸庞搭配精致淡妆，五官清秀立体，眼神温婉地望向镜头。画面采用电影级光照，人物轮廓分明，发丝锐利清晰。背景是黄昏时分的古典园林，暖色调光影交织。高清半身特写，近景构图突出肌肤质感与服饰细节，呈现东方女性优雅气质。\",\"actual_prompt\":\"商业摄影风格，18岁中国女孩身着淡雅蓝色旗袍，立领盘扣，袖口绣着金色祥云暗纹。她圆润脸庞搭配精致淡妆，五官清秀立体，眼神温婉地望向镜头。画面采用电影级光照，人物轮廓分明，发丝锐利清晰。背景是黄昏时分的古典园林，暖色调光影交织。高清半身特写，近景构图突出肌肤质感与服饰细节，呈现东方女性优雅气质。\",\"url\":\"https://dashscope-result-wlcb-acdr-1.oss-cn-wulanchabu-acdr-1.aliyuncs.com/1d/64/20250303/8928fb36/e1d1562c-8139-4426-8bb3-be2f37738d212399250839.png?Expires=1741099657&OSSAccessKeyId=LTAI5tKPD3TMqf2Lna1fASuh&Signature=vOOPEEnoeYljhBHcOeVj1bABc7A%3D\"},{\"orig_prompt\":\"商业摄影风格，18岁中国女孩身着淡雅蓝色旗袍，立领盘扣，袖口绣着金色祥云暗纹。她圆润脸庞搭配精致淡妆，五官清秀立体，眼神温婉地望向镜头。画面采用电影级光照，人物轮廓分明，发丝锐利清晰。背景是黄昏时分的古典园林，暖色调光影交织。高清半身特写，近景构图突出肌肤质感与服饰细节，呈现东方女性优雅气质。\",\"actual_prompt\":\"商业摄影风格，18岁中国女孩身着淡雅蓝色旗袍，立领盘扣，袖口绣着金色祥云暗纹。她圆润脸庞搭配精致淡妆，五官清秀立体，眼神温婉地望向镜头。画面采用电影级光照，人物轮廓分明，发丝锐利清晰。背景是黄昏时分的古典园林，暖色调光影交织。高清半身特写，近景构图突出肌肤质感与服饰细节，呈现东方女性优雅气质。\",\"url\":\"https://dashscope-result-wlcb-acdr-1.oss-cn-wulanchabu-acdr-1.aliyuncs.com/1d/d1/20250303/b0fe3396/e1d1562c-8139-4426-8bb3-be2f37738d212399250838.png?Expires=1741099657&OSSAccessKeyId=LTAI5tKPD3TMqf2Lna1fASuh&Signature=v36OQ0FP%2B50Xy3fVj2c1MeDbSQQ%3D\"}],\"task_metrics\":{\"TOTAL\":2,\"SUCCEEDED\":2,\"FAILED\":0}},\"usage\":{\"image_count\":2}}\n";

    public static void main(String[] args) {
        AliyunTextToPicTools test = new AliyunTextToPicTools();
        List<String> urlFromJson = test.getUrlFromJson(test.json);
        for (String url : urlFromJson
             ) {
            System.out.println("URL: " + url);
        }

    }

    public static List<String>  getUrlFromJson(String jsonString) {
        ArrayList<String> objects = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将JSON字符串解析为Map对象
            Map<String, Object> map = objectMapper.readValue(jsonString, Map.class);
            // 获取output对象
            Map<String, Object> output = (Map<String, Object>) map.get("output");
            // 获取results列表
            List<Map<String, Object>> results = (List<Map<String, Object>>) output.get("results");
            // 获取第一个result对象
            for (int i=0;i<results.size();i++){
                Map<String, Object> result = results.get(i);
                String s = (String) result.get("url");
                objects.add(s);
            }

            // 获取url参数
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

