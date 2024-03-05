package com.example.webflux.service;

import com.example.webflux.client.PostClient;
import com.example.webflux.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    //webclient mvc server request
    private final PostClient postClient;


    @Override
    public Mono<PostResponse> getPostContent(Long id) {
        return postClient.getPost(id)
                .onErrorResume(error -> Mono.just(new PostResponse(id.toString(), "Fallback data %d".formatted(id))));
    }


    public Flux<PostResponse> getMultiplePostContent(List<Long> idList) {
        return Flux.fromIterable(idList)
                .flatMap(this::getPostContent)
                .log();

    }

    public Flux<PostResponse> getParallelMultiplePostContent(List<Long> idList) {
        return Flux.fromIterable(idList)
                .parallel() // 병렬로 작업을 진행
                .runOn(Schedulers.parallel())
                .flatMap(this::getPostContent)
                .log()
                .sequential(); // 결과를 merge 해주는 역할

    }
}
