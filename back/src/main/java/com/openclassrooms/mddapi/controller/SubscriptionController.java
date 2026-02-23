package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing user subscriptions to topics.
 *
 * <p>
 * This controller exposes endpoints to:
 * <ul>
 *     <li>Subscribe to a topic</li>
 *     <li>Unsubscribe from a topic</li>
 *     <li>Retrieve the authenticated user's subscribed topics</li>
 * </ul>
 * </p>
 *
 * <p>
 * All endpoints are mapped under the base path <b>/subscriptions</b>.
 * </p>
 */
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * Constructs a {@link SubscriptionController} with the required {@link SubscriptionService}.
     *
     * @param subscriptionService the service responsible for subscription business logic
     */
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Subscribes the authenticated user to a specific topic.
     *
     * <p>
     * The authenticated user's identifier is extracted from the security context
     * and used to create the subscription relationship.
     * </p>
     *
     * @param topicId        the unique identifier of the topic to subscribe to
     * @param authentication the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} with HTTP 200 status if the subscription is successful
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
    @PostMapping("/{topicId}")
    public ResponseEntity<Void> subscribe(
            @PathVariable Long topicId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        subscriptionService.subscribe(user.getId(), topicId);

        return ResponseEntity.ok().build();
    }

    /**
     * Unsubscribes the authenticated user from a specific topic.
     *
     * <p>
     * The authenticated user's identifier is extracted from the security context
     * and used to remove the subscription relationship.
     * </p>
     *
     * @param topicId        the unique identifier of the topic to unsubscribe from
     * @param authentication the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} with HTTP 204 (No Content) status if the operation is successful
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable Long topicId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        subscriptionService.unsubscribe(user.getId(), topicId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all topics subscribed to by the authenticated user.
     *
     * @param authentication the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} containing a list of {@link TopicDto}
     *         representing the user's subscribed topics
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
    @GetMapping("/me")
    public ResponseEntity<List<TopicDto>> getMySubscriptions(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        List<TopicDto> subscriptions =
                subscriptionService.getUserTopics(user.getId());

        return ResponseEntity.ok(subscriptions);
    }
}