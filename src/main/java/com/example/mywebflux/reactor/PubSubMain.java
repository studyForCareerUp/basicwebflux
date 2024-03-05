package com.example.mywebflux.reactor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PubSubMain {

    public static void main(String[] args) {
        Publisher publisher = new Publisher();

        // 1) startFlux
        publisher.startFlux()
                .subscribe(n -> log.info(n.toString()));

        System.out.println();

        // 2) startFlux2
        publisher.startFlux2()
                .subscribe();

        System.out.println();

        // 3) startMono
        publisher.startMono()
                .subscribe(n -> log.info(n.toString()));

        System.out.println();

        // 4) startMono2
        publisher.startMono2()
                .subscribe();


    }


}
