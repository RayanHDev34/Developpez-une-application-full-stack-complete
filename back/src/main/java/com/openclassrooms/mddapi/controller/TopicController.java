package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.request.TopicRequest;
import com.openclassrooms.mddapi.payload.response.TopicResponse;
import com.openclassrooms.mddapi.service.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing topic-related operations.
 *
 * <p>
 * This controller exposes endpoints to:
 * <ul>
 *     <li>Retrieve all available topics with subscription status for the authenticated user</li>
 *     <li>Create a new topic</li>
 * </ul>
 * </p>
 *
 * <p>
 * All endpoints are mapped under the base path <b>/topics</b>.
 * </p>
 */
@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    /**
     * Constructs a {@link TopicController} with the required {@link TopicService}.
     *
     * @param topicService the service responsible for topic business logic
     */
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Retrieves all topics along with the subscription status
     * for the authenticated user.
     *
     * <p>
     * The authenticated user's identifier is extracted from the security context
     * to determine which topics the user is currently subscribed to.
     * </p>
     *
     * @param authentication the authentication object containing the currently authenticated user
     * @return a {@link ResponseEntity} containing a list of {@link TopicResponse}
     *         including subscription information
     * @throws ClassCastException if the authentication principal cannot be cast to {@link User}
     */
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAll(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        List<TopicResponse> topicResponses =
                topicService.getAllTopicsWithSubscription(user.getId());

        return ResponseEntity.ok(topicResponses);
    }

    /**
     * Creates a new topic.
     *
     * <p>
     * The topic information is provided in the request body
     * and passed to the service layer for creation.
     * </p>
     *
     * @param request the request containing topic creation data
     * @return a {@link ResponseEntity} containing the created {@link TopicDto}
     */
    @PostMapping
    public ResponseEntity<TopicDto> create(@RequestBody TopicRequest request) {

        TopicDto topic = topicService.createTopic(request);

        return ResponseEntity.ok(topic);
    }
}