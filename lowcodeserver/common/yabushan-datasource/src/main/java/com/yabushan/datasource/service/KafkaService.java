package com.yabushan.datasource.service;

import com.yabushan.datasource.config.kafka.DefaultKafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaService {
    @Autowired
    private DefaultKafkaUtils defaultKafkaUtils;

    public String produce( String topic ,String value) throws Exception {
        return defaultKafkaUtils.sendMessage(topic,value);
     
    }
    public void consumer(String topicName) throws Exception {
        defaultKafkaUtils.consumeJsonData(topicName);
    }
    
    
    public void createTopic(String topicName) throws Exception {
      defaultKafkaUtils.createTopic(topicName);
    }
    
    public List<String>  listTopics(){
        List<String> strings = defaultKafkaUtils.listTopics();
        return strings;
    }
    
    
    
    
}
