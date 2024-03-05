package com.example.webflux.service;

import com.example.webflux.repository.User;
import com.example.webflux.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    //create update delete read

    public Mono<User> create(String name, String email) {
        return userRepository.save(User.builder().name(name).email(email).build());

    }

    public Flux<User> findAll() {
        return userRepository.findAll();

    }

    private String getUserCashKey(Long id) {
        return "users:%d".formatted(id);
    }

    public Mono<User> findById(Long id) {

        return userRepository.findById(id);
    }

    public Mono<Void> deleteById(Long id) {
        return userRepository.deleteById(id)
                .then(Mono.empty());
    }


    public Mono<User> update(Long id, String name, String email) {
        // 1. 해당 사용자를 찾는다.
        // 2. 데이터를 변경하고 저장한다.
        return userRepository.findById(id)
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
                    return userRepository.save(u);
                });

    }


}
