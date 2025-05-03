package com.yabushan.datasource.config.kafka;

/**
 * @author chensx
 * date 2023/11/2 4:48 下午
 * version 2.0
 **/
public class KafkaConfigConstant {

    public static final String SERVERS = "kafka.servers";
    public static final String AUTHENTICATION = "kafka.authentication";
    public static final String USER = "kafka.user";
    public static final String PASSWORD = "kafka.password";
    public static final String TOPIC = "topic";
    public static final String PARTITION = "partition";

    public static final String ZOOKEEPER = "zookeeper.connect";

    public static final String SSLSERVERS = "ssl.truststore.servers";

    public static final String SSLLOCATION = "ssl.truststore.location";

    public static final String SSLPASSWORD = "ssl.truststore.password";

    public static final String SSLACTIVE = "ssl.truststore.active";

    public static final String ACKS = "producer.acks";

    public static final String RETRIES = "producer.retries";

    public static final String BATCHSIZE = "producer.batch-size";

    public static final String groupId = "groupId";

    public KafkaConfigConstant() {
    }
}
