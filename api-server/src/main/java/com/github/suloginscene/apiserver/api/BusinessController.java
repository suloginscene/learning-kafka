package com.github.suloginscene.apiserver.api;

import com.github.suloginscene.apiserver.producer.BusinessLogProducer;
import com.github.suloginscene.apiserver.producer.BusinessProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessProducer producer;
    private final BusinessLogProducer businessLogProducer;

    @PostMapping("/api/{val}")
    public void api(@PathVariable String val) {
        producer.send(val);
    }

    @PostMapping("/api/log-iter/{iter}")
    public void logIter(@PathVariable Integer iter) {
        businessLogProducer.send("start logIter " + iter);
        for (int i = 0; i < iter; i++) {
            businessLogProducer.send("iter " + i);
        }
        businessLogProducer.send("stop logIter " + iter);
    }

}
