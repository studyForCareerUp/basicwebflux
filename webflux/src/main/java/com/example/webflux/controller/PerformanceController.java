package com.example.webflux.controller;

import com.example.webflux.repository.User;
import com.example.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performance")
public class PerformanceController {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final UserRepository userRepository;

    @GetMapping("/health")
    public Mono<Map<String, String>> health() {
        return Mono.just(Map.of("health", "ok"));
    }

    @GetMapping("/users/1/cache")
    public Mono<Map<String, String>> getCachedUser() {
        var name = reactiveRedisTemplate.opsForValue().get("users:1:name");
        var email = reactiveRedisTemplate.opsForValue().get("users:1:email");

        return Mono.zip(name, email)
                .map(i -> Map.of("name", i.getT1(), "email", i.getT2()));
    }

    @GetMapping("/users/{id}")
    public Mono<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id).defaultIfEmpty(new User());
    }
}
