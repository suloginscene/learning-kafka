package com.github.suloginscene.logcollector;

import com.github.suloginscene.logcollector.consumer.LogConsumerThread;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class LogConsumerThreadRunner implements ApplicationRunner {

    private LogConsumerThread logConsumerThread;


    @Override
    public void run(ApplicationArguments args) {
        logConsumerThread = new LogConsumerThread();
        logConsumerThread.start();
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        logConsumerThread.kill();
        logConsumerThread.join();
    }

}
