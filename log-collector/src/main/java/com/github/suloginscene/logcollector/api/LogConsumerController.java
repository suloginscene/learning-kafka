package com.github.suloginscene.logcollector.api;

import com.github.suloginscene.logcollector.LogConsumerThreadRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogConsumerController {

    private final LogConsumerThreadRunner logConsumerThreadRunner;


    @PostMapping("/add-thread/{consumerGroup}")
    public void add(@PathVariable String consumerGroup) {
        logConsumerThreadRunner.addAndRunThread(consumerGroup);
    }

}
