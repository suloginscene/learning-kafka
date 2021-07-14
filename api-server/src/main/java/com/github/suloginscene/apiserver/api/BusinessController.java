package com.github.suloginscene.apiserver.api;

import com.github.suloginscene.apiserver.producer.BusinessProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessProducer producer;

    @GetMapping("/api/{val}")
    public void api(@PathVariable String val) {
        producer.send(val);
    }

}
