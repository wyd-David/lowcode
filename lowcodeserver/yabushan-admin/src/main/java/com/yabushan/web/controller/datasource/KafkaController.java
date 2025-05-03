package com.yabushan.web.controller.datasource;

import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.datasource.service.KafkaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "kafka数据服务")
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaService kafkaService;
    @ApiOperation("创建一个主题")
    @RequestMapping(value = "/createTopic", method = RequestMethod.POST)
    public AjaxResult createTopic(String topicName) throws Exception {
        if(StringUtils.isNotNull(topicName)){
            kafkaService.createTopic(topicName);
        }
        return  AjaxResult.success("创建成功");
    }
    @ApiOperation("列出所有主题")
    @RequestMapping(value = "/listTopic", method = RequestMethod.POST)
    public AjaxResult listTopic(){
        List<String> strings = kafkaService.listTopics();
        return  AjaxResult.success(strings);
    }

    @ApiOperation("推送kafka主题数据")
    @RequestMapping(value = "/produce", method = RequestMethod.POST)
    public AjaxResult produce(String topic,String message){
        try {
           kafkaService.produce(topic, message);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }

        return AjaxResult.success("操作成功");
    }


    @ApiOperation("测试kafka消费主题数据")
    @RequestMapping(value = "/consumer", method = RequestMethod.POST)
    public  void consumer(String topic) throws Exception {
        kafkaService.consumer(topic);
    }
    

}
