package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.UserTopic;
import com.openclassrooms.mddapi.payload.request.TopicRequest;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.UserTopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service responsible for managing topic-related business logic.
 *
 * <p>
 * This service handles:
 * <ul>
 *     <li>Retrieving all available topics</li>
 *     <li>Creating new topics</li>
 *     <li>Retrieving topics along with user subscription status</li>
 * </ul>
 * </p>
 */
@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserTopicRepository userTopicRepository;
    private final UserRepository userRepository;
    private final TopicMapper topicMapper;

    /**
     * Constructs a {@link TopicService} with required dependencies.
     *
     * @param topicRepository     repository for topic persistence
     * @param topicMapper         mapper used to convert between entities and DTOs
     * @param userTopicRepository repository managing user-topic associations
     * @param userRepository      repository for user persistence
     */
    public TopicService(
            TopicRepository topicRepository,
            TopicMapper topicMapper,
            UserTopicRepository userTopicRepository,
            UserRepository userRepository
    ) {
        this.topicRepository = topicRepository;
        this.userTopicRepository = userTopicRepository;
        this.userRepository = userRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Retrieves all available topics.
     *
     * @return a list of {@link TopicDto} representing all topics in the system
     */
    public List<TopicDto> getAllTopics() {
        return topicMapper.toDtoList(topicRepository.findAll());
    }

    /**
     * Creates a new topic.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Checks if a topic with the same name already exists</li>
     *     <li>Maps the request to a {@link Topic} entity</li>
     *     <li>Persists the topic</li>
     *     <li>Returns a DTO representation</li>
     * </ol>
     * </p>
     *
     * @param request the topic creation request containing name and description
     * @return the created {@link TopicDto}
     * @throws RuntimeException if a topic with the same name already exists
     */
    public TopicDto createTopic(TopicRequest request) {

        if (topicRepository.existsByName(request.getName())) {
            throw new RuntimeException("Topic already exists");
        }

        Topic topic = topicMapper.toEntity(request);
        topicRepository.save(topic);

        return topicMapper.toDto(topic);
    }

    /**
     * Retrieves all topics along with the subscription status for a specific user.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Retrieves all topic subscriptions for the user</li>
     *     <li>Extracts subscribed topic identifiers</li>
     *     <li>Retrieves all topics from the database</li>
     *     <li>Builds a {@link TopicResponse} including a boolean flag indicating
     *     whether the user is subscribed</li>
     * </ol>
     * </p>
     *
     * @param userID the identifier of the user
     * @return a list of {@link TopicResponse} containing topic information
     *         and subscription status
     */
    public List<TopicResponse> getAllTopicsWithSubscription(Long userID) {

        List<UserTopic> userTopics = userTopicRepository.findByUserId(userID);

        Set<Long> subscribedTopicIds = userTopics.stream()
                .map(ut -> ut.getTopic().getId())
                .collect(Collectors.toSet());

        return topicRepository.findAll()
                .stream()
                .map(topic -> new TopicResponse(
                        topic.getId(),
                        topic.getName(),
                        topic.getDescription(),
                        subscribedTopicIds.contains(topic.getId())
                ))
                .toList();
    }
}