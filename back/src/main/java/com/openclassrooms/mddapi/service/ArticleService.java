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

@Service
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TopicRepository topicRepository;
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final UserTopicRepository userTopicRepository;

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

    public ArticleDto createArticle(Long userId,
                                         ArticleRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Article article = ArticleMapper.toEntity(request, user, topic);

        articleRepository.save(article);

        return ArticleMapper.toDto(article);
    }
    public ArticleDetailResponse getArticleById(Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        ArticleDto articleDto = ArticleMapper.toDto(article);
        List<CommentResponse> comments =
                commentService.getCommentsByArticle(id);

        return new ArticleDetailResponse(articleDto, comments);
    }
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
