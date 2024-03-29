package com.example.webflux.reactor;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class Operator4Test {

    Operator4 operator4 = new Operator4();
    @Test
    void fluxDelayAndLimit() {
        StepVerifier.create(operator4.fluxDelayAndLimit())
                .expectNext(1,2,3,4,5,6,7,8,9,10)
                .verifyComplete();
    }

    @Test
    void fluxSample() {
        StepVerifier.create(operator4.fluxSample())
                .expectNextCount(35)
                .verifyComplete();
    }
}