package com.example.webflux.service;

import com.example.webflux.repository.User;
import com.example.webflux.repository.UserR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    // 1) 내부 메모리 사용
//    private final UserRepository userRepository;

    // 2) r2dbc db 사용
    private final UserR2dbcRepository userR2dbcRepository;

    // 3) redis cache 사용
    private final ReactiveRedisTemplate<String, User> reactiveRedisTemplate;

    //create update delete read

    public Mono<User> create(String name, String email) {
//        return userRepository.save(User.builder().name(name).email(email).build());
        return userR2dbcRepository.save(User.builder().name(name).email(email).build());

    }

    public Flux<User> findAll() {
//        return userRepository.findAll();
        return userR2dbcRepository.findAll();

    }

    private String getUserCashKey(Long id) {
        return "users:%d".formatted(id);
    }

    public Mono<User> findById(Long id) {

//        return userRepository.findById(id);
//        return userR2dbcRepository.findById(id);
        return reactiveRedisTemplate.opsForValue()
                .get(getUserCashKey(id))
                .switchIfEmpty(
                        userR2dbcRepository.findById(id)
                                .flatMap(u -> reactiveRedisTemplate.opsForValue()
                                        .set(getUserCashKey(id), u, Duration.ofSeconds(30))
                                        .then(Mono.just(u))
                                )
                                .log());
    }

    public Mono<Void> deleteById(Long id) {
//        return userRepository.deleteById(id)
//                .then(Mono.empty());
        return userR2dbcRepository.deleteById(id)
                .then(reactiveRedisTemplate.unlink(getUserCashKey(id))) //redis cache 에서 삭제하는 로직 추가
                .then(Mono.empty());
    }


    public Mono<Void> deleteByName(String name) {
        return userR2dbcRepository.deleteByName(name);
    }


    public Mono<User> update(Long id, String name, String email) {
        // 1. 해당 사용자를 찾는다.
        // 2. 데이터를 변경하고 저장한다.
//        return userRepository.findById(id)
//                //empty 처리 -> switchIfEmpty, defaultItEmpty
//                // 이렇게 할 경우 Mono<Mono<User>> 타입이 리턴되기 때문에 안된다.
////                .map(u ->{
////                    u.setName(name);
////                    u.setEmail(email);
////                    return  userRepository.save(u);
////                });
//                .flatMap(u -> {
//                    u.setName(name);
//                    u.setEmail(email);
//                    return userRepository.save(u);
//                });

        return userR2dbcRepository.findById(id)
                //empty 처리 -> switchIfEmpty, defaultItEmpty
                // 이렇게 할 경우 Mono<Mono<User>> 타입이 리턴되기 때문에 안된다.
//                .map(u ->{
//                    u.setName(name);
//                    u.setEmail(email);
//                    return  userRepository.save(u);
//                });
                .flatMap(u -> {
                    u.setName(name);
                    u.setEmail(email);
                    return userR2dbcRepository.save(u);
                })
                .flatMap(u -> reactiveRedisTemplate.unlink(getUserCashKey(id)) // redis cache 에서 삭제하는 로직 추가
                        .then(Mono.just(u)));


    }


}
