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

/**
 * REST controller responsible for managing article-related operations.
 *
 * <p>
 * This controller exposes endpoints to:
 * <ul>
 *     <li>Create a new article</li>
 *     <li>Retrieve a specific article by its identifier</li>
 *     <li>Retrieve the authenticated user's article feed</li>
 * </ul>
 * </p>
 *
 * <p>
 * All endpoints are mapped under the base path <b>/articles</b>.
 * </p>
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * Constructs an {@link ArticleController} with the required {@link ArticleService}.
     *
     * @param articleService the service responsible for article business logic
     */
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Creates a new article for the authenticated user.
     *
     * <p>
     * The authenticated user's identifier is extracted from the security context
     * and passed to the service layer along with the article request payload.
     * </p>
     *
     * @param request         the article creation request containing title, content, and other data
     * @param authentication  the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} containing the created {@link ArticleDto}
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
    @PostMapping
    public ResponseEntity<ArticleDto> createArticle(
            @RequestBody ArticleRequest request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        ArticleDto article =
                articleService.createArticle(user.getId(), request);

        return ResponseEntity.ok(article);
    }

    /**
     * Retrieves the detailed information of an article by its identifier.
     *
     * @param id the unique identifier of the article
     * @return a {@link ResponseEntity} containing the {@link ArticleDetailResponse}
     *         with full article details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDetailResponse> getArticle(@PathVariable Long id) {

        ArticleDetailResponse articleDetailResponse = articleService.getArticleById(id);
        return ResponseEntity.ok(articleDetailResponse);
    }

    /**
     * Retrieves the article feed for the authenticated user.
     *
     * <p>
     * The feed typically contains articles relevant to the user,
     * such as articles from subscribed authors or topics.
     * </p>
     *
     * @param authentication the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} containing a list of {@link ArticleDto}
     *         representing the user's feed
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
    @GetMapping
    public ResponseEntity<List<ArticleDto>> getFeed(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        List<ArticleDto> articles =
                articleService.getFeed(user.getId());

        return ResponseEntity.ok(articles);
    }
}