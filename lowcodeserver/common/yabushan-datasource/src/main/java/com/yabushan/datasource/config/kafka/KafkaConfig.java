package com.yabushan.datasource.config.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chensx
 * date 2023/11/3 2:55 下午
 * version 2.0
 **/


@Data
@Component
public class KafkaConfig {

    @Value("${kafka.servers}")
    private String servers;

    @Value("${kafka.authentication}")
    private String authentication;

    @Value("${kafka.user}")
    private String user;

    @Value("${kafka.password}")
    private String password;

    @Value("${topic}")
    private String topic;

    @Value("${zookeeper.connect}")
    private String zookeeperConnect;

    @Value("${ssl.truststore.location}")
    private String sslLocation;

    @Value("${ssl.truststore.password}")
    private String sslPassword;

    @Value("${ssl.active}")
    private String sslActive;

    @Value("${ssl.truststore.servers}")
    private String sslServers;


    @Value("${partition}")
    private String partition;

    @Value("${producer.acks}")
    private String ack;

    @Value("${producer.retries}")
    private String retries;

    @Value("${producer.batch-size}")
    private String batchSize;

    @Value("${groupId}")
    private String groupId;

}
