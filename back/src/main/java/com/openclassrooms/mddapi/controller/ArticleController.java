package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.response.ArticleDetailResponse;
import com.openclassrooms.mddapi.service.ArticleService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(
            @RequestBody ArticleRequest request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        ArticleDto article =
                articleService.createArticle(user.getId(), request);

        return ResponseEntity.ok(article);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDetailResponse> getArticle(@PathVariable Long id) {

        ArticleDetailResponse articleDetailResponse = articleService.getArticleById(id);
        return ResponseEntity.ok(articleDetailResponse);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getFeed(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        List<ArticleDto> articles =
                articleService.getFeed(user.getId());

        return ResponseEntity.ok(articles);
    }
}
