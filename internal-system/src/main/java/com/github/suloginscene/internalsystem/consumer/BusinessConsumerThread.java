package com.github.suloginscene.internalsystem.consumer;

import com.github.suloginscene.internalsystem.producer.InternalLogProducer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@RequiredArgsConstructor
public class BusinessConsumerThread extends Thread {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "internal-consumer";
    private static final String STRING_DESERIALIZER = StringDeserializer.class.getName();

    private static final String TOPIC_NAME = "business";
    private static final Duration TIMEOUT = Duration.ofSeconds(1L);

    private static final String LOG_FORMAT = "[BusinessConsumer] %s";

    private final InternalLogProducer producer;


    private AtomicBoolean running;


    @Override
    public void run() {
        producer.send(format("run"));

        running = new AtomicBoolean(true);

        KafkaConsumer<String, String> consumer = createConsumer();
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

        while (running.get()) {
            consumer.poll(TIMEOUT)
                    .forEach((record) -> producer.send(format("consume " + record.value())));
        }

        consumer.close();

        producer.send(format("killed"));
    }

    private String format(String text) {
        return String.format(LOG_FORMAT, text);
    }

    private KafkaConsumer<String, String> createConsumer() {
        Properties configs = new Properties();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(GROUP_ID_CONFIG, GROUP_ID);
        configs.put(KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        configs.put(VALUE_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        return new KafkaConsumer<>(configs);
    }

    public void kill() {
        running.set(false);
    }

}
