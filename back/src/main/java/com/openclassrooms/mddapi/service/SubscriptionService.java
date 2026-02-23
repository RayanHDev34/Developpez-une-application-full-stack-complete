package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.UserTopic;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.UserTopicRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for managing user subscriptions to topics.
 *
 * <p>
 * This service handles:
 * <ul>
 *     <li>Subscribing a user to a topic</li>
 *     <li>Unsubscribing a user from a topic</li>
 *     <li>Retrieving all topics a user is subscribed to</li>
 * </ul>
 * </p>
 *
 * <p>
 * All operations are executed within a transactional context.
 * </p>
 */
@Service
@Transactional
public class SubscriptionService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final UserTopicRepository userTopicRepository;
    private final TopicMapper topicMapper;

    /**
     * Constructs a {@link SubscriptionService} with required dependencies.
     *
     * @param userRepository      repository for user persistence
     * @param topicRepository     repository for topic persistence
     * @param userTopicRepository repository for managing user-topic relationships
     * @param topicMapper         mapper used to convert {@link Topic} to {@link TopicDto}
     */
    public SubscriptionService(
            UserRepository userRepository,
            TopicRepository topicRepository,
            UserTopicRepository userTopicRepository,
            TopicMapper topicMapper
    ) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.userTopicRepository = userTopicRepository;
        this.topicMapper = topicMapper;
    }

    /**
     * Subscribes a user to a specific topic.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Checks if the subscription already exists</li>
     *     <li>Validates the existence of the user</li>
     *     <li>Validates the existence of the topic</li>
     *     <li>Creates and persists a {@link UserTopic} association</li>
     * </ol>
     * </p>
     *
     * @param userId  the identifier of the user
     * @param topicId the identifier of the topic
     * @throws RuntimeException if the user or topic does not exist,
     *                          or if the user is already subscribed
     */
    public void subscribe(Long userId, Long topicId) {

        if (userTopicRepository.existsByUserIdAndTopicId(userId, topicId)) {
            throw new RuntimeException("Already subscribed");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        UserTopic subscription = new UserTopic();
        subscription.setUser(user);
        subscription.setTopic(topic);

        userTopicRepository.save(subscription);
    }

    /**
     * Unsubscribes a user from a specific topic.
     *
     * <p>
     * This method removes the association between the user and the topic.
     * If no subscription exists, the operation completes silently.
     * </p>
     *
     * @param userId  the identifier of the user
     * @param topicId the identifier of the topic
     */
    public void unsubscribe(Long userId, Long topicId) {
        userTopicRepository.deleteByUserIdAndTopicId(userId, topicId);
    }

    /**
     * Retrieves all topics subscribed to by a user.
     *
     * <p>
     * The method:
     * <ol>
     *     <li>Fetches all {@link UserTopic} associations for the user</li>
     *     <li>Extracts the related {@link Topic} entities</li>
     *     <li>Maps them to {@link TopicDto}</li>
     * </ol>
     * </p>
     *
     * @param userId the identifier of the user
     * @return a list of {@link TopicDto} representing the user's subscribed topics
     */
    public List<TopicDto> getUserTopics(Long userId) {

        return userTopicRepository.findByUserId(userId)
                .stream()
                .map(UserTopic::getTopic)
                .map(topicMapper::toDto)
                .collect(Collectors.toList());
    }
}