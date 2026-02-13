package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.payload.request.TopicRequest;
import com.openclassrooms.mddapi.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicService(TopicRepository topicRepository,
                        TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
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
}
