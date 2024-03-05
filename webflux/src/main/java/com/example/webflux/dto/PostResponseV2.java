package com.example.webflux.dto;

import com.example.webflux.repository.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PostResponseV2 {
    private Long id;

    private Long userId;

    private String title;

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponseV2 of(Post post) {
        return PostResponseV2.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .content(post.getContent())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

}
