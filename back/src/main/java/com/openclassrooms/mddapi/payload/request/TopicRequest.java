package com.openclassrooms.mddapi.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicRequest {

    @NotBlank(message = "Topic name is required")
    @Size(max = 100, message = "Topic name must not exceed 100 characters")
    private String name;

    @NotBlank(message = "Topic description is required")
    @Size(max = 1000, message = "Topic description must not exceed 1000 characters")
    private String description;
}
