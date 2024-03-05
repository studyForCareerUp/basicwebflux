package com.example.webmvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    @GetMapping("{id}")
    public Map<String, String> getId(@PathVariable String id) {
        return Map.of("id", id, "content", "Posts content %d".formatted(id));
    }

}

