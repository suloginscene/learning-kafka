package com.github.suloginscene.logcollector.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

@Slf4j
@RequiredArgsConstructor
public class LogConsumerThread extends Thread {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String STRING_DESERIALIZER = StringDeserializer.class.getName();

    private static final String TOPIC_NAME = "log";
    private static final Duration TIMEOUT = Duration.ofSeconds(5L);

    private final String groupId;

    private AtomicBoolean running;


    @Override
    public void run() {
        log.info("------- run {} {}-------", this.getClass().getSimpleName(), groupId);

        running = new AtomicBoolean(true);

        KafkaConsumer<String, String> consumer = createConsumer();
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

        while (running.get()) {
            consumer.poll(TIMEOUT).forEach((r) ->
                    log.info("\n> {}\n\ttopic: {}, partition: {}, offset: {}\n\tkey: {}, value: {}\n",
                            groupId, r.topic(), r.partition(), r.offset(), r.key(), r.value())
            );
        }

        consumer.close();

        log.info("------- killed {} {}-------", this.getClass().getSimpleName(), groupId);
    }

    private KafkaConsumer<String, String> createConsumer() {
        Properties configs = new Properties();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        configs.put(GROUP_ID_CONFIG, groupId);
        configs.put(KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        configs.put(VALUE_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        return new KafkaConsumer<>(configs);
    }

    public void kill() {
        running.set(false);
    }

}
