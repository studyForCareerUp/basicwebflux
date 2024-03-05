package com.example.mywebflux.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Publisher {

    // Flux 는 0-N 개 아이템을 가질 수 있는 데이터 스트림이다.
    // .log() 옵션을 통해서 logging 이 가능
    public Flux<Integer> startFlux() {
//        Flux.just(1, 2, 3, 4, 5);
//        Flux.fromIterable(List.of("a", "b", "c"));
        return Flux.range(1, 10).log();

    }

    public Flux<String> startFlux2() {
        return Flux.fromIterable(List.of("a", "b", "c", "d")).log();

    }

    // MONO 는 0-1 개 아이템을 가질 수 있는 데이터 스트림이다.
    public Mono<Integer> startMono() {
        return Mono.just(1).log();
    }


    public Mono<?> startMono2() {
        return Mono.empty().log();
    }

    public Mono<?> startMono3() {
        return Mono.error(new Exception("hello reactor"));
    }
}
