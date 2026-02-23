package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for managing comment-related business logic.
 *
 * <p>
 * This service handles:
 * <ul>
 *     <li>Creating comments associated with an article and a user</li>
 *     <li>Retrieving comments for a specific article</li>
 * </ul>
 * </p>
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    /**
     * Constructs a {@link CommentService} with required dependencies.
     *
     * @param commentRepository repository for comment persistence
     * @param articleRepository repository for article retrieval
     * @param userRepository    repository for user retrieval
     * @param commentMapper     mapper used to convert between entities and responses
     */
    public CommentService(CommentRepository commentRepository,
                          ArticleRepository articleRepository,
                          UserRepository userRepository,
                          CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Creates a new comment associated with a specific article and user.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Validates the existence of the user</li>
     *     <li>Validates the existence of the article</li>
     *     <li>Creates a new {@link Comment} entity</li>
     *     <li>Persists the comment</li>
     *     <li>Returns a {@link CommentResponse}</li>
     * </ol>
     * </p>
     *
     * @param articleId the identifier of the article being commented on
     * @param content   the textual content of the comment
     * @param userID    the identifier of the user creating the comment
     * @return the created {@link CommentResponse}
     * @throws RuntimeException if the user or article does not exist
     */
    public CommentResponse createComment(
            Long articleId,
            String content,
            Long userID
    ) {

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        Comment comment = commentMapper.toEntity(content, user, article);

        commentRepository.save(comment);

        return commentMapper.toResponse(comment);
    }

    /**
     * Retrieves all comments associated with a specific article.
     *
     * <p>
     * Comments are ordered by creation date in descending order
     * (most recent comments first).
     * </p>
     *
     * @param articleId the identifier of the article
     * @return a list of {@link CommentResponse} representing the article's comments
     */
    public List<CommentResponse> getCommentsByArticle(Long articleId) {

        List<Comment> comments =
                commentRepository.findByArticleIdOrderByCreatedAtDesc(articleId);

        return commentMapper.toResponseList(comments);
    }
}