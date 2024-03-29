package com.example.webflux.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Scheduler1 {
    public Flux<Integer> fluxMapWithSubscribeOn() {
        return Flux.range(1, 10)
                .map(i -> i * 2)
                .subscribeOn(Schedulers.boundedElastic()) // subscribeOn 에는 Schedulers 온다.
                .log();
    }

    public Flux<Integer> fluxMapWithPublishOn() {
        return Flux.range(1, 10)
                .map(i -> i * 1)
                .publishOn(Schedulers.boundedElastic()) // publishOn 도 마찬가지로 Schedulers 온다.
                .log()
                .publishOn(Schedulers.parallel())
                .log()
                .map(i -> i * 2)
                .log();
    }
}
