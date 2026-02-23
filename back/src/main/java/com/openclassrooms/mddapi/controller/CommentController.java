package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.CommentRequest;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing comment-related operations.
 *
 * <p>
 * This controller exposes endpoints to:
 * <ul>
 *     <li>Create a comment on a specific article</li>
 *     <li>Retrieve all comments associated with an article</li>
 * </ul>
 * </p>
 *
 * <p>
 * All comment-related endpoints are mapped under the base path <b>/articles</b>,
 * as comments are considered a sub-resource of articles.
 * </p>
 */
@RestController
@RequestMapping("/articles")
public class CommentController {

    private final CommentService commentService;

    /**
     * Constructs a {@link CommentController} with the required {@link CommentService}.
     *
     * @param commentService the service responsible for comment business logic
     */
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Creates a new comment for a specific article.
     *
     * <p>
     * The authenticated user's identifier is extracted from the security context
     * and used to associate the comment with its author.
     * </p>
     *
     * @param articleId       the unique identifier of the article on which the comment is posted
     * @param request         the request containing the comment content
     * @param authentication  the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} containing the created {@link CommentResponse}
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
    @PostMapping("/{articleId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long articleId,
            @RequestBody CommentRequest request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        CommentResponse response = commentService.createComment(
                articleId,
                request.getContent(),
                user.getId()
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all comments associated with a specific article.
     *
     * @param articleId the unique identifier of the article
     * @return a {@link ResponseEntity} containing a list of {@link CommentResponse}
     *         representing all comments linked to the article
     */
    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long articleId) {

        return ResponseEntity.ok(
                commentService.getCommentsByArticle(articleId)
        );
    }
}