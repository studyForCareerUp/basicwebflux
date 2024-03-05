package com.example.webmvc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class WebmvcApplication implements ApplicationListener<ApplicationReadyEvent> {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebmvcApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        redisTemplate.opsForValue().set("users:1:name", "greg");
        redisTemplate.opsForValue().set("users:1:email", "greg@fastcampus.co.kr");

        Optional<User> user = userRepository.findById(1L);
        if (user.isEmpty()) {
            userRepository.save(User.builder().name("greg").email("greg@fastcampus.co.kr").build());
        }
    }
}
