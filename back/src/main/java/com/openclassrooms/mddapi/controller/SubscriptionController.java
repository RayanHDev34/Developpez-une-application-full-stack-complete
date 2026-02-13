package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{topicId}")
    public ResponseEntity<Void> subscribe(
            @PathVariable Long topicId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        subscriptionService.subscribe(user.getId(), topicId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable Long topicId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        subscriptionService.unsubscribe(user.getId(), topicId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    public ResponseEntity<List<TopicDto>> getMySubscriptions(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        List<TopicDto> subscriptions =
                subscriptionService.getUserTopics(user.getId());

        return ResponseEntity.ok(subscriptions);
    }
}
