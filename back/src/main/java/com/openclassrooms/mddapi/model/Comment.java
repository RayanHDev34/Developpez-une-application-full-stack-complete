package com.openclassrooms.mddapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a comment posted by a user on an article.
 *
 * <p>
 * A comment:
 * <ul>
 *     <li>Contains textual content</li>
 *     <li>Is associated with a specific article</li>
 *     <li>Is authored by a user</li>
 *     <li>Has a creation timestamp automatically set before persistence</li>
 * </ul>
 * </p>
 *
 * <p>
 * Each comment is stored in the "comments" table.
 * </p>
 */
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    /**
     * Unique identifier of the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Textual content of the comment.
     */
    @Column(nullable = false)
    private String content;

    /**
     * Timestamp indicating when the comment was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Author of the comment.
     * Represents a many-to-one relationship with {@link User}.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    /**
     * Article associated with the comment.
     * Represents a many-to-one relationship with {@link Article}.
     */
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    /**
     * Lifecycle callback executed before the entity is persisted.
     *
     * <p>
     * Automatically sets the creation timestamp.
     * </p>
     */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}