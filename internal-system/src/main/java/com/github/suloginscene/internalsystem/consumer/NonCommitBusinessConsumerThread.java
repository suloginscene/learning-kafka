package com.github.suloginscene.internalsystem.consumer;

import com.github.suloginscene.internalsystem.producer.InternalLogProducer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class NonCommitBusinessConsumerThread extends BusinessConsumerThread {

    public NonCommitBusinessConsumerThread(InternalLogProducer producer) {
        super(producer);
    }

    @Override
    protected void doCommit(KafkaConsumer<String, String> consumer) {
        System.out.println("do nothing");
    }

}
