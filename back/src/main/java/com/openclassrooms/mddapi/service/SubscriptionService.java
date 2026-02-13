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

@Service
@Transactional
public class SubscriptionService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final UserTopicRepository userTopicRepository;
    private final TopicMapper topicMapper;

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

    public void unsubscribe(Long userId, Long topicId) {
        userTopicRepository.deleteByUserIdAndTopicId(userId, topicId);
    }

    public List<TopicDto> getUserTopics(Long userId) {
        return userTopicRepository.findByUserId(userId)
                .stream()
                .map(UserTopic::getTopic)
                .map(topicMapper::toDto)
                .collect(Collectors.toList());
    }
}
