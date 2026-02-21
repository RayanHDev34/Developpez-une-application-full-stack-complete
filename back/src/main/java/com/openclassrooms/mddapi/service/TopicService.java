package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
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

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserTopicRepository userTopicRepository;
    private final UserRepository userRepository;
    private final TopicMapper topicMapper;

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

    public List<TopicDto> getAllTopics() {
        return topicMapper.toDtoList(topicRepository.findAll());
    }

    public TopicDto createTopic(TopicRequest request) {

        if (topicRepository.existsByName(request.getName())) {
            throw new RuntimeException("Topic already exists");
        }
        Topic topic = topicMapper.toEntity(request);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }
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
