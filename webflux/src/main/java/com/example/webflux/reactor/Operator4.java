package com.example.webflux.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Operator4 {

    //limit
    public Flux<Integer> fluxDelayAndLimit() {
        return Flux.range(1, 10)
                .delaySequence(Duration.ofSeconds(1)) // 얼만큼 지연시켜서 보낼건지
                .log()
                .limitRate(3); //한번 지연될때 얼만큼 보낼건지
    }

    //sample
    public Flux<Integer> fluxSample() {
        return Flux.range(1, 100)
                .delayElements(Duration.ofMillis(100))
                .sample(Duration.ofMillis(300)) //얼만큼의 주기만큼 스킵해서 보낼건지
                .log();
    }
}
