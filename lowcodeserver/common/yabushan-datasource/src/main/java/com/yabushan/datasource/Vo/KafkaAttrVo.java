package com.yabushan.datasource.Vo;

import lombok.Data;

@Data
public class KafkaAttrVo {
    /**
     * jmx_port
     * connect_url_str
     * schema_listener_port
     * authentication
     * zookeeper_config
     * timeout
     * trustStoreFilePassword
     * port
     * kafka_node_json
     * trustStoreFile
     * kafka_home* 
     */
    
    private String jmxPort;
    private String connectUrlStr;
    private String schemaListenerPort;
    private String authentication;
    private String zookeeperConfig;
    private String timeOut;
    private String trustStoreFilePassword;
    private String port;
    private String kafkaNodeJson;
    private String trustStoreFile;
    private String kafkaHome;
}
