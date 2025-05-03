package com.yabushan.datasource.config.kafka;
import com.google.gson.Gson;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author chensx
 * date 2023/11/2 11:30 上午
 * version 2.0
 **/

@Component
public class DefaultKafkaUtils {

    @Autowired
    KafkaConfig kafkaConfig;

    private Gson gson = new Gson();
    private Logger logger = LoggerFactory.getLogger(DefaultKafkaUtils.class);

    /**
     * 获取系统内置kafka连接属性
     * @return
     * @throws Exception
     */
    public Map<String,String> getConnectParams(){
        Map<String,String> connectParams = new HashMap<>();

        connectParams.put(KafkaConfigConstant.SERVERS,kafkaConfig.getServers());
        connectParams.put(KafkaConfigConstant.AUTHENTICATION, kafkaConfig.getAuthentication());
        connectParams.put(KafkaConfigConstant.USER,kafkaConfig.getUser());
        connectParams.put(KafkaConfigConstant.PASSWORD, kafkaConfig.getPassword());
        connectParams.put(KafkaConfigConstant.ZOOKEEPER,kafkaConfig.getZookeeperConnect());
        connectParams.put(KafkaConfigConstant.PARTITION,String.valueOf(kafkaConfig.getPartition()));
        connectParams.put(KafkaConfigConstant.TOPIC,kafkaConfig.getTopic());


        //证书配置
        connectParams.put(KafkaConfigConstant.SSLSERVERS,kafkaConfig.getSslServers());
        connectParams.put(KafkaConfigConstant.SSLLOCATION,kafkaConfig.getSslLocation());
        connectParams.put(KafkaConfigConstant.SSLPASSWORD, kafkaConfig.getSslPassword());
        connectParams.put(KafkaConfigConstant.SSLACTIVE,kafkaConfig.getSslActive());

        connectParams.put(KafkaConfigConstant.ACKS,kafkaConfig.getAck());
        connectParams.put(KafkaConfigConstant.RETRIES, kafkaConfig.getRetries());
        connectParams.put(KafkaConfigConstant.BATCHSIZE,kafkaConfig.getBatchSize());

        connectParams.put(KafkaConfigConstant.groupId,kafkaConfig.getGroupId());

        return connectParams;
    }

    /**
     * 获取kafka客户端对象
     * @param connectParams
     * @return
     */
    public static AdminClient getBingoKafkaClient(Map<String,String> connectParams){
        Properties properties = getConnectProperties(connectParams);
        properties.put("zookeeper.connect",connectParams.get(KafkaConfigConstant.ZOOKEEPER));
        AdminClient adminClient = AdminClient.create(properties);
        return adminClient;
    }

    /**
     * 获取kafka连接属性
     * @param connectParams
     * @return
     */
    public static Properties getConnectProperties(Map<String,String> connectParams){
        Properties properties = new Properties();
        fillAuthentication(properties,connectParams);
        return properties;
    }

    /**
     *  获取kafka的消费参数
     * @param connectParams
     * @return
     */
    public static Properties getConsumerProperties(Map<String,String> connectParams){
        Properties properties = getConnectProperties(connectParams);

        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);


//        String id = UUID.randomUUID().toString();
        String id = connectParams.get(KafkaConfigConstant.groupId);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,id);
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG,id);
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        if(isCloudEvent){
//            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CloudEventDeserializer.class);
//        }
//        else{
//            properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        }

        //必须要加，如果要读旧数据
        properties.put("auto.offset.reset", "earliest");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "100");
        //每次拉取的消息字节数，10K？，每次取回20条左右
        properties.put("max.partition.fetch.bytes", "10240");
        properties.put("session.timeout.ms", "30000");


        return properties;
    }

    /**
     * 填充验证信息
     * @param properties
     */
    private static void fillAuthentication(Properties properties, Map<String,String> connectParams){

        String server = connectParams.get(KafkaConfigConstant.SERVERS);
        String authentication = connectParams.get(KafkaConfigConstant.AUTHENTICATION);
        String user = connectParams.get(KafkaConfigConstant.USER);
        String password = connectParams.get(KafkaConfigConstant.PASSWORD);


        String sslServer = connectParams.get(KafkaConfigConstant.SSLSERVERS);
        String sslLocation = connectParams.get(KafkaConfigConstant.SSLLOCATION);
        String sslPassword = connectParams.get(KafkaConfigConstant.SSLPASSWORD);
        String sslActive = connectParams.get(KafkaConfigConstant.SSLACTIVE);
        System.out.println("authentication参数为============"+authentication);
        if (authentication != null && authentication.startsWith("SASL")) {
            String jaasConfig = null;
            if (authentication.equals("SASL/PLAIN")) {
                properties.put("sasl.mechanism", "PLAIN");
                jaasConfig = "org.apache.kafka.common.security.plain.PlainLoginModule required username='" + user + "' password='" + password + "';";
            } else if (authentication.equals("SASL/SCRAM")) {
                properties.put("sasl.mechanism", "SCRAM-SHA-512");
                jaasConfig = "org.apache.kafka.common.security.scram.ScramLoginModule required username='" + user + "' password='" + password + "';";
            }
            System.out.println("获取到的jaasConfig========"+jaasConfig);
            properties.put(SaslConfigs.SASL_JAAS_CONFIG, jaasConfig);
        }

        if("true".equals(sslActive)){
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,sslServer);
            properties.put("security.protocol", "SASL_SSL");
            properties.put(SslConfigs.SSL_ENDPOINT_IDENTIFICATION_ALGORITHM_CONFIG,"");
            properties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG,sslLocation);
            properties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,sslPassword);
        }else{
            properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
            properties.put("security.protocol", "SASL_PLAINTEXT");
        }

        //默认值 先注释
        String acks = connectParams.get(KafkaConfigConstant.ACKS);
        String retries = connectParams.get(KafkaConfigConstant.RETRIES);
        String batchSize = connectParams.get(KafkaConfigConstant.BATCHSIZE);
        properties.put("acks", acks);
        properties.put("retries", retries);
        properties.put("batch.size", batchSize);

    }

    /**
     * 获取消费体
     * @param map
     * @return
     */
    public static KafkaConsumer<String, String> getConsumer(Map<String,String> map) {
        Properties properties = getConsumerProperties(map);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        return consumer;
    }

    static String status = "success";
    public static String sendToKafka(String topic, String message, Map<String,String> map){

        Properties properties = getConnectProperties(map);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);


        KafkaProducer<String, String> producer = null;
        // String key = String.valueOf(UUID.randomUUID());
        try{
            producer = new KafkaProducer<String, String>(properties);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic,message);
            Future<RecordMetadata> send = producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                        status = e.getMessage();
                    }
                }
            });
            //  System.out.println("发送消息成功到主题："+topic+"，key："+key);
        }catch (Exception ex){
            ex.printStackTrace();
            return  ex.getMessage();
        }finally {
            producer.close();
        }

        return status;
    }


    /**
     * 获取生产者
     * @return
     */
    public  KafkaProducer<String, String> getProducer(){
        Map<String,String> connectParams = getConnectParams();
        Properties properties = getConnectProperties(connectParams);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        KafkaProducer<String, String> producer =  new KafkaProducer<String, String>(properties);
        return producer;
    }

    //传入生产者并推送数据
    public  String sendDataToKafka(String topic,String message,KafkaProducer<String, String> producer){
        ProducerRecord<String, String> record = new ProducerRecord<>(topic,message);
        Future<RecordMetadata> send = producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (e != null) {
                    e.printStackTrace();
                    status = e.getMessage();
                }
            }
        });
        return status;
    }

    /**
     * 创建主题
     *
     * @param topic
     */
    public int createTopic(String topic) throws Exception {
        List<String> topicList = new ArrayList<>();
        topicList.add(topic);
        return createTopics(topicList);
    }

    /**
     * 创建主题
     *
     * @param topicList
     */
    public int createTopics(List<String> topicList) throws Exception {
        int status = 1;
        Map<String, String> connectParams = getConnectParams();
        int partition = 1;//主题的分区数量
        AdminClient client = null;
        try {
            client = getBingoKafkaClient(connectParams);
            if (client != null) {
                ArrayList newTopics = new ArrayList();
                for (String topic : topicList) {
                    NewTopic newTopic = new NewTopic(topic, Integer.parseInt(connectParams.get(KafkaConfigConstant.PARTITION)), (short) 1);
                    newTopics.add(newTopic);
                }

                client.createTopics(newTopics).all().get();
            }

        }
        catch (Exception e) {
            logger.error("创建事件主题失败", e);
            status = 0;
            throw new Exception(e.getMessage());
        } finally {
            if(client!=null){
                client.close();
            }
        }
        return status;
    }

    /**
     * 获取主题列表
     * @return
     */
    public List<String> listTopics(){
        Map<String,String> connectParams = getConnectParams();
        List<String> topicList = null;
        AdminClient client = null;
        try {
            client = getBingoKafkaClient(connectParams);
            if(client!=null){
                Collection<TopicListing> topicListings = client.listTopics().listings().get();
                if(topicListings!=null&&topicListings.size()>0){
                    topicList = topicListings.stream().map(TopicListing::name).collect(Collectors.toList());
                }
            }
        }
        catch (Exception e){
            logger.error("获取主题列表失败",e);
        }
        finally {
            if(client!=null){
                client.close();
            }
        }
        return topicList;
    }


    public String sendMessage(String topic,String message){
        Map<String,String> connectParams = getConnectParams();
        //connectParams.get(KafkaConfigConstant.TOPIC)
        String status = sendToKafka(topic,message,connectParams);
        return status;
    }




    /**
     * 获取事件数据
     * @param topic
     * @return
     */
    public void consumeJsonData(String topic){
        System.out.println("消费的主题为"+topic);
        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,String> connectParams = getConnectParams();
        KafkaConsumer<String, String> consumer = getConsumer(connectParams);
        consumer.subscribe(Arrays.asList(topic.split(",")));


        try{
            while (true){
                System.out.println("开始消费。。。。。。");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));

                //处理消息
                for (ConsumerRecord<String, String> consumerRecord : records) {
                    try{
//                        String record = consumerRecord.value();
//                        Map<String, Object> recordMap = gson.fromJson(record, new TypeToken<Map<String, Object>>() {}.getType());
//                        result.add(recordMap);
                        System.out.println("fetched from partition " + consumerRecord.partition() + ", offset: " + consumerRecord.offset() + ",key: " + consumerRecord.key() + ",value:" + consumerRecord.value() );
                    }
                    catch (Exception e){
                        System.out.println("消费异常。。。。。。");
                        logger.error("获取kafka示例数据转换失败",e);
                    }
                }

                try {
                    // 使用异步提交规避阻塞
                    consumer.commitAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }catch (Exception ex ){
            ex.printStackTrace();
        }finally {
            //最后一次提交使用同步阻塞式提交
            consumer.commitSync();
            consumer.close();
        }



    }

    /**
     * 删除主题
     * @param topic
     */
    public void deleteTopic(String topic) throws Exception {
        List<String> topicList = new ArrayList<>();
        topicList.add(topic);
        deleteTopics(topicList);
    }
    /**
     * 删除主题
     * @param topicList
     */
    public void deleteTopics(List<String> topicList) throws Exception {
        Map<String,String> connectParams = getConnectParams();
        int partition = 1;//主题的分区数量
        AdminClient client = null;
        try{
            client = getBingoKafkaClient(connectParams);
            if(client!=null){
              /*  ArrayList newTopics = new ArrayList();
                for(String topic: topicList){
                    NewTopic newTopic = new NewTopic(topic, Integer.parseInt(connectParams.get(KafkaConfigConstant.PARTITION)), (short) 1);
                    newTopics.add(newTopic);
                }*/
                client.deleteTopics(topicList);
            }

        }
        catch (Exception e){
            logger.error("删除主题失败",e);
            throw new Exception(e.getMessage());
        }
        finally {
            if(client!=null){
                client.close();
            }
        }
    }
}
