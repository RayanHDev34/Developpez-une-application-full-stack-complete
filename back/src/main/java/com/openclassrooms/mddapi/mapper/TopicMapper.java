package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.payload.request.TopicRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicMapper {

    public Topic toEntity(TopicRequest request) {
        if (request == null) {
            return null;
        }
        Topic topic = new Topic();
        topic.setName(request.getName());
        topic.setDescription(request.getDescription());
        return topic;
    }

    public TopicDto toDto(Topic topic) {
        if (topic == null) {
            return null;
        }

        return new TopicDto(
                topic.getId(),
                topic.getName(),
                topic.getDescription()
        );
    }

    public List<TopicDto> toDtoList(List<Topic> topics) {
        return topics.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
