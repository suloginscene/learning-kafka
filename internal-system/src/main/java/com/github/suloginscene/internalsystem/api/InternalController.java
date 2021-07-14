package com.github.suloginscene.internalsystem.api;

import com.github.suloginscene.internalsystem.BusinessConsumerThreadRunner;
import com.github.suloginscene.internalsystem.consumer.BusinessConsumerThread;
import com.github.suloginscene.internalsystem.consumer.NonCommitBusinessConsumerThread;
import com.github.suloginscene.internalsystem.producer.InternalLogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InternalController {

    private final BusinessConsumerThreadRunner businessConsumerThreadRunner;
    private final InternalLogProducer internalLogProducer;

    @PostMapping("/stop")
    public void stopThread() throws InterruptedException {
        businessConsumerThreadRunner.stop();
    }

    @PostMapping("/run")
    public void runThread() {
        businessConsumerThreadRunner.run(null);
    }

    @PutMapping("/{type}")
    public void switchThread(@PathVariable String type) throws InterruptedException {
        stopThread();

        businessConsumerThreadRunner.setBusinessConsumerThread(
                type.equals("non-commit")
                        ? new NonCommitBusinessConsumerThread(internalLogProducer)
                        : new BusinessConsumerThread(internalLogProducer)
        );

        runThread();
    }

}
