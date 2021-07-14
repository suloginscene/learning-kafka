package com.github.suloginscene.internalsystem;

import com.github.suloginscene.internalsystem.consumer.BusinessConsumerThread;
import com.github.suloginscene.internalsystem.producer.InternalLogProducer;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@RequiredArgsConstructor
public class BusinessConsumerThreadRunner implements ApplicationRunner {

    private final InternalLogProducer internalLogProducer;

    @Setter
    private BusinessConsumerThread businessConsumerThread;


    @PostConstruct
    public void init() {
        businessConsumerThread = new BusinessConsumerThread(internalLogProducer);
    }

    @Override
    public void run(ApplicationArguments args) {
        businessConsumerThread.start();
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        businessConsumerThread.kill();
        businessConsumerThread.join();
    }

}
