package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String topicName;
    private LocalDateTime createdAt;
}