package com.app.modules.config;

import com.app.modules.interceptors.ProducerInterceptorPrefix;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String servers;

    @Value(value = "${spring.kafka.producer.batch-size}")
    private String batchSize;

    @Value(value = "${spring.kafka.producer.buffer-memory}")
    private String bufferMemory;

    @Value(value = "${spring.kafka.producer.compression-type}")
    private String compressType;

    @Value(value = "${spring.kafka.producer.retries}")
    private int retries;

    @Value(value = "${spring.kafka.producer.linger.ms}")
    private int linger;

    private Map<String, Object> config(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressType);
        config.put(ProducerConfig.RETRIES_CONFIG, retries);
        config.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        config.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ProducerInterceptorPrefix.class.getName());

        // 必须添加一下配置否则报错：
        // org.apache.kafka.common.config.ConfigException:
        // Missing required configuration "key.serializer" which has no default value.
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return config;
    }

    private ProducerFactory<Object, Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(config());
    }

    @Bean(name = "kafkaTemplate")
    public KafkaTemplate kafkaTemplate(){
        return new KafkaTemplate(producerFactory());
    }

}
