package com.example.webflux.reactor;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

// reactor 검증은 StepVerifier 통해서 한다.
class Operator1Test {

    private Operator1 operation1 = new Operator1();

    @Test
    void fluxMap() {
        StepVerifier.create(operation1.fluxMap())
                .expectNext(2, 4, 6, 8, 10)
                .verifyComplete();
    }

    @Test
    void fluxFilter() {
        StepVerifier.create(operation1.fluxFilter())
                .expectNext(6, 7, 8, 9, 10)
                .verifyComplete();
    }

    @Test
    void fluxFilterTake() {
        StepVerifier.create(operation1.fluxFilterTake())
                .expectNext(6, 7, 8)
                .verifyComplete();
    }

    @Test
    void fluxFlatMap() { //flatmap , 반드시 순서 보장 X(비동기 작업일 경우)
        StepVerifier.create(operation1.fluxFlatMap())
                .expectNextCount(100)
                .verifyComplete();
    }

    @Test
    void fluxFlatMap2() {
        StepVerifier.create(operation1.fluxFlatMap2())
                .expectNextCount(81)
                .verifyComplete();
    }

    @Test
    void fluxConcatMap() {
        StepVerifier.create(operation1.fluxConcatMap())
                .expectNextCount(100)
                .verifyComplete();
    }
}