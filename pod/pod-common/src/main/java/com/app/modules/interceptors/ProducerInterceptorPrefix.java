package com.app.modules.interceptors;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProducerInterceptorPrefix implements ProducerInterceptor<String, String> {

    private static final Logger log = LoggerFactory.getLogger(ProducerInterceptorPrefix.class);

    //成功次数
    private AtomicLong sendSuccess = new AtomicLong(0);

    //失败次数
    private AtomicLong sendFailure = new AtomicLong(0);

    //发送次数统计
    private AtomicInteger count = new AtomicInteger(0);

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        String json = producerRecord.value();
        count.addAndGet(1);
        if(count.get() == 1000){
            log.info("pause "+count.get());
        }
        return new ProducerRecord<>(producerRecord.topic()
                , producerRecord.partition()
                , producerRecord.key()
                , producerRecord.value()
                , producerRecord.headers());
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if(e == null){
            sendSuccess.addAndGet(1);
        }else{
            sendFailure.addAndGet(1);
        }
    }

    @Override
    public void close() {
        long successRatio = sendSuccess.get()/(sendSuccess.get()+sendFailure.get());
        log.info("发送成功率 {} %", successRatio*100);
        log.info("总共发送了{} 条数据!", count);
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
