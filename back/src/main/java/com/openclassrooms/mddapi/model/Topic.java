package com.openclassrooms.mddapi.model;

import jdk.jshell.Snippet;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "topics")
@Getter
@Setter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

}
