package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.UserTopic;
import com.openclassrooms.mddapi.payload.request.TopicRequest;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.repository.UserTopicRepository;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    public TopicController(
            TopicService topicService
    ) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAll( Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<TopicResponse> topicResponses = topicService.getAllTopicsWithSubscription(user.getId());
        return ResponseEntity.ok(topicResponses);
    }

    @PostMapping
    public ResponseEntity<TopicDto> create(@RequestBody TopicRequest request) {
        TopicDto topic = topicService.createTopic(request);
        return ResponseEntity.ok(topic);
    }
}

