package com.example.webflux.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SampleController {

    @GetMapping("sample/hello")
    public Mono<String> getHello() {

        // reactor
        // publisher <-----> subscriber
        // 원래는 항상 publisher 와 subscriber 있어야 하지만, 여기서는 spring webflux 가 subscriber 내부적으로 구현하고 있어 별도로 작성 안해줘도 된다.

        return Mono.just("hello rest controller with webflux");
    }
}
