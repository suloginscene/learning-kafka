package com.github.suloginscene.logcollector;

import com.github.suloginscene.logcollector.consumer.LogConsumerThread;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class LogConsumerThreadRunner implements ApplicationRunner {

    private LogConsumerThread logConsumerThread1;
    private LogConsumerThread logConsumerThread2;


    @Override
    public void run(ApplicationArguments args) {
        logConsumerThread1 = new LogConsumerThread("log-consumer-1");
        logConsumerThread2 = new LogConsumerThread("log-consumer-2");

        logConsumerThread1.start();
        logConsumerThread2.start();
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        logConsumerThread1.kill();
        logConsumerThread2.kill();

        logConsumerThread1.join();
        logConsumerThread2.join();
    }

}
