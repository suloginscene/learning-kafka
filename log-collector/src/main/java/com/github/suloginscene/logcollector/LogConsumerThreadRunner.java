package com.github.suloginscene.logcollector;

import com.github.suloginscene.logcollector.consumer.LogConsumerThread;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class LogConsumerThreadRunner implements ApplicationRunner {

    private final String consumerGroup1 = "log-consumer-1";
    private final String consumerGroup2 = "log-consumer-2";

    private final Set<LogConsumerThread> threadGroup1 = new HashSet<>();
    private final Set<LogConsumerThread> threadGroup2 = new HashSet<>();

    private final Map<String, Set<LogConsumerThread>> nameGroupMap = new HashMap<>();
    private final Map<String, StopWatch> groupWatchMap = new HashMap<>();


    @PostConstruct
    public void init() {
        nameGroupMap.put(consumerGroup1, threadGroup1);
        nameGroupMap.put(consumerGroup2, threadGroup2);

        groupWatchMap.put(consumerGroup1, new StopWatch());
        groupWatchMap.put(consumerGroup2, new StopWatch());
    }

    @Override
    public void run(ApplicationArguments args) {
        threadGroup1.add(new LogConsumerThread(consumerGroup1, groupWatchMap.get(consumerGroup1)));
        threadGroup2.add(new LogConsumerThread(consumerGroup2, groupWatchMap.get(consumerGroup2)));

        threadGroup1.forEach(Thread::start);
        threadGroup2.forEach(Thread::start);
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        threadGroup1.forEach(LogConsumerThread::kill);
        threadGroup2.forEach(LogConsumerThread::kill);

        for (LogConsumerThread logConsumerThread : threadGroup1) logConsumerThread.join();
        for (LogConsumerThread logConsumerThread : threadGroup2) logConsumerThread.join();

        threadGroup1.clear();
        threadGroup2.clear();
    }

    public void addAndRunThread(String consumerGroup) {
        LogConsumerThread newThread = new LogConsumerThread(consumerGroup, groupWatchMap.get(consumerGroup));
        newThread.start();

        Set<LogConsumerThread> threadGroup = nameGroupMap.get(consumerGroup);
        threadGroup.add(newThread);
    }

}
