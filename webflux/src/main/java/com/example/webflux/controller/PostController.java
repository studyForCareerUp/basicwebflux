package com.example.webflux.controller;

import com.example.webflux.dto.PostResponse;
import com.example.webflux.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    // reactor
    // publisher <-----> subscriber
    // 원래는 항상 publisher 와 subscriber 있어야 하지만, 여기서는 spring webflux 가 subscriber 내부적으로 구현하고 있어 별도로 작성 안해줘도 된다.


    private final PostService postService;

    @GetMapping("/{id}")
    public Mono<PostResponse> getPostContent(@PathVariable Long id) throws InterruptedException {


        return postService.getPostContent(id);
    }

    @GetMapping("/search")

    public Flux<PostResponse> getMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) throws InterruptedException {
        Thread.sleep(3000);
        return postService.getMultiplePostContent(idList);

    }


    @GetMapping("/search/parallel")

    public Flux<PostResponse> getParallelMultiplePostContent(@RequestParam(name = "ids") List<Long> idList) throws InterruptedException {
        Thread.sleep(3000);
        return postService.getParallelMultiplePostContent(idList);

    }
}
