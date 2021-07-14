package com.github.suloginscene.internalsystem.api;

import com.github.suloginscene.internalsystem.BusinessConsumerThreadRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InternalController {

    private final BusinessConsumerThreadRunner businessConsumerThreadRunner;

    @PostMapping("/stop")
    public void stopThread() throws InterruptedException {
        businessConsumerThreadRunner.stop();
    }

    @PostMapping("/run")
    public void runThread() {
        businessConsumerThreadRunner.run(null);
    }

}
