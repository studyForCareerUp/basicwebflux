package com.example.webflux.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    // Create Update
    Mono<User> save(User user);
    // Read
    Flux<User> findAll();
    // Read
    Mono<User> findById(Long id);
    // Delete
    Mono<Integer> deleteById(Long Id);


}
