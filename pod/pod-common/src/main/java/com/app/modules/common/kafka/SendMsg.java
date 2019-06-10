package com.app.modules.common.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息生产者
 */

@Component
public class SendMsg {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value(value = "${spring.kafka.producer.topic}")
    private String topic;

    private Gson gson = new GsonBuilder().create();

    public void send(Object msg){
        kafkaTemplate.send(topic, gson.toJson(msg));
    }

}
