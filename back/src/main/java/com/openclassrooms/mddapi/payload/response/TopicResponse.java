package com.openclassrooms.mddapi.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopicResponse {

    private Long id;
    private String name;
    private String description;
    private boolean subscribed;

    public TopicResponse(Long id, String name, String description, boolean subscribed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subscribed = subscribed;
    }

    // getters
}
