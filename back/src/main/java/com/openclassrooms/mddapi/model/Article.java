package com.openclassrooms.mddapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents an article published by a user within a specific topic.
 *
 * <p>
 * An article contains:
 * <ul>
 *     <li>A title</li>
 *     <li>Content (up to 5000 characters)</li>
 *     <li>A creation timestamp</li>
 *     <li>An author (User)</li>
 *     <li>An associated topic</li>
 * </ul>
 * </p>
 *
 * <p>
 * Each article is persisted in the "articles" table.
 * </p>
 */
@Entity
@Table(name = "articles")
@Getter
@Setter
public class Article {

    /**
     * Unique identifier of the article.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the article.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Main textual content of the article.
     */
    @Column(nullable = false, length = 5000)
    private String content;

    /**
     * Timestamp indicating when the article was created.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Author of the article.
     * Represents a many-to-one relationship with {@link User}.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    /**
     * Topic under which the article is published.
     * Represents a many-to-one relationship with {@link Topic}.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "topic_id")
    private Topic topic;
}