package com.example.webmvc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performance")
@Slf4j
public class PerformanceController {
    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;

    @GetMapping("/health")
    public Map<String, String> heatlh() {
//        log.info("health");
        return Map.of("health", "ok");
    }

    @GetMapping("/users/1/cache")
    public Map<String, String> getCachedUser() {
        var name = redisTemplate.opsForValue().get("users:1:name");
        var email = redisTemplate.opsForValue().get("users:1:email");
        return Map.of("name", name == null ? "" : name, "email", email == null ? "" : email);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(new User());
    }

}
