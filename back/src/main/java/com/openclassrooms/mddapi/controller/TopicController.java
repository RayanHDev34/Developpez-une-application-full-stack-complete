package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.payload.request.TopicRequest;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> getAll() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    @PostMapping
    public ResponseEntity<TopicDto> create(@RequestBody TopicRequest request) {

        TopicDto topic = topicService.createTopic(request);

        return ResponseEntity.ok(topic);
    }
}

