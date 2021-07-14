package com.github.suloginscene.internalsystem.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
@Slf4j
public class InternalLogProducer {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String STRING_SERIALIZER = StringSerializer.class.getName();

    private static final String TOPIC_NAME = "log";

    private KafkaProducer<String, String> producer;


    @PostConstruct
    public void init() {
        Properties configs = new Properties();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, STRING_SERIALIZER);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, STRING_SERIALIZER);

        producer = new KafkaProducer<>(configs);
    }

    public void send(String value) {
        producer.send(
                new ProducerRecord<>(TOPIC_NAME, String.valueOf(value.hashCode()), value),
                (metadata, exception) -> {
                    if (exception == null) log.info("send success: {}", value);
                    else log.error("send fail: {}", exception.toString());
                }
        );
    }

}
