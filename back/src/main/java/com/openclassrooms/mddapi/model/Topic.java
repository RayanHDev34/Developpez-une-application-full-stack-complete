package com.openclassrooms.mddapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents a discussion topic within the system.
 *
 * <p>
 * A topic:
 * <ul>
 *     <li>Has a unique name</li>
 *     <li>Contains a description</li>
 *     <li>Can be associated with multiple articles</li>
 *     <li>Can have multiple subscribed users</li>
 * </ul>
 * </p>
 *
 * <p>
 * Topics are stored in the "topics" table.
 * </p>
 */
@Entity
@Table(name = "topics")
@Getter
@Setter
public class Topic {

    /**
     * Unique identifier of the topic.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique name of the topic.
     * Maximum length: 100 characters.
     */
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    /**
     * Description of the topic.
     * Maximum length: 1000 characters.
     */
    @Column(nullable = false, length = 1000)
    private String description;
}