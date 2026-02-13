package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicDto {

    private Long id;
    private String name;
    private String description;

    public TopicDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // getters
}
