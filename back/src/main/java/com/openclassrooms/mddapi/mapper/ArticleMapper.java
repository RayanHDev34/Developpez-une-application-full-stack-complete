package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;

import java.time.LocalDateTime;

public class ArticleMapper {

    public static Article toEntity(ArticleRequest request,
                                   User author,
                                   Topic topic) {

        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setCreatedAt(LocalDateTime.now());
        article.setAuthor(author);
        article.setTopic(topic);

        return article;
    }

    public static ArticleDto toDto(Article article) {
        ArticleDto dto = new ArticleDto();

        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setAuthorName(article.getAuthor().getUsername());
        dto.setTopicName(article.getTopic().getName());
        dto.setCreatedAt(article.getCreatedAt());

        return dto;
    }
}
