package com.openclassrooms.mddapi.model;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the association between a {@link User} and a {@link Topic}.
 *
 * <p>
 * This entity models the subscription relationship where:
 * <ul>
 *     <li>A user can subscribe to multiple topics</li>
 *     <li>A topic can have multiple subscribed users</li>
 * </ul>
 * </p>
 *
 * <p>
 * It corresponds to the "users_topics" join table in the database.
 * </p>
 */
@Entity
@Table(name = "users_topics")
@Getter
@Setter
@NoArgsConstructor
public class UserTopic {

    /**
     * Unique identifier of the subscription.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The subscribed user.
     * Represents a many-to-one relationship with {@link User}.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The subscribed topic.
     * Represents a many-to-one relationship with {@link Topic}.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id")
    private Topic topic;
}