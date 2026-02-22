package com.openclassrooms.mddapi.payload.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponse {
    private Integer id;
    private String content;
    private LocalDateTime createdAt;
    private String authorName;
}


