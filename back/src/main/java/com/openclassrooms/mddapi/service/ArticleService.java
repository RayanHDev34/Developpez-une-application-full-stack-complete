package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.mapper.ArticleMapper;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.UserTopic;
import com.openclassrooms.mddapi.payload.request.ArticleRequest;
import com.openclassrooms.mddapi.payload.response.ArticleDetailResponse;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.UserTopicRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service responsible for managing article-related business logic.
 *
 * <p>
 * This service handles:
 * <ul>
 *     <li>Article creation</li>
 *     <li>Article retrieval with associated comments</li>
 *     <li>User feed generation based on topic subscriptions</li>
 * </ul>
 * </p>
 *
 * <p>
 * All operations are executed within a transactional context.
 * </p>
 */
@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TopicRepository topicRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final UserTopicRepository userTopicRepository;

    /**
     * Constructs an {@link ArticleService} with required repositories and services.
     *
     * @param articleRepository   repository for article persistence
     * @param topicRepository     repository for topic persistence
     * @param userRepository      repository for user persistence
     * @param commentService      service responsible for comment retrieval
     * @param userTopicRepository repository for managing user-topic subscriptions
     */
    public ArticleService(
            ArticleRepository articleRepository,
            TopicRepository topicRepository,
            UserRepository userRepository,
            CommentService commentService,
            UserTopicRepository userTopicRepository
    ) {
        this.articleRepository = articleRepository;
        this.topicRepository = topicRepository;
        this.commentService = commentService;
        this.userRepository = userRepository;
        this.userTopicRepository = userTopicRepository;
    }

    /**
     * Creates a new article associated with a given user and topic.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Validates the existence of the user</li>
     *     <li>Validates the existence of the topic</li>
     *     <li>Maps the request to an {@link Article} entity</li>
     *     <li>Persists the article</li>
     *     <li>Returns a DTO representation</li>
     * </ol>
     * </p>
     *
     * @param userId  the identifier of the article author
     * @param request the article creation request containing title, content, and topic identifier
     * @return the created {@link ArticleDto}
     * @throws RuntimeException if the user or topic does not exist
     */
    public ArticleDto createArticle(Long userId, ArticleRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Article article = ArticleMapper.toEntity(request, user, topic);

        articleRepository.save(article);

        return ArticleMapper.toDto(article);
    }

    /**
     * Retrieves an article by its identifier along with its associated comments.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Fetches the article from the database</li>
     *     <li>Maps it to a DTO</li>
     *     <li>Retrieves all related comments</li>
     *     <li>Aggregates them into an {@link ArticleDetailResponse}</li>
     * </ol>
     * </p>
     *
     * @param id the unique identifier of the article
     * @return an {@link ArticleDetailResponse} containing article details and comments
     * @throws RuntimeException if the article does not exist
     */
    public ArticleDetailResponse getArticleById(Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        ArticleDto articleDto = ArticleMapper.toDto(article);

        List<CommentResponse> comments =
                commentService.getCommentsByArticle(id);

        return new ArticleDetailResponse(articleDto, comments);
    }

    /**
     * Generates the feed for a specific user based on their topic subscriptions.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Retrieves all topics the user is subscribed to</li>
     *     <li>Extracts the topic identifiers</li>
     *     <li>Fetches articles belonging to those topics</li>
     *     <li>Sorts them by creation date (descending)</li>
     *     <li>Maps them to DTOs</li>
     * </ol>
     * </p>
     *
     * @param userId the identifier of the user
     * @return a list of {@link ArticleDto} representing the user's feed.
     *         Returns an empty list if the user has no subscriptions.
     */
    public List<ArticleDto> getFeed(Long userId) {

        List<UserTopic> subscriptions =
                userTopicRepository.findByUserId(userId);

        List<Long> topicIds = subscriptions.stream()
                .map(userTopic -> userTopic.getTopic().getId())
                .toList();

        if (topicIds.isEmpty()) {
            return List.of();
        }

        List<Article> articles =
                articleRepository.findByTopic_IdInOrderByCreatedAtDesc(topicIds);

        return articles.stream()
                .map(ArticleMapper::toDto)
                .toList();
    }
}