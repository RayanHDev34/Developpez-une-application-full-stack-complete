package com.openclassrooms.mddapi.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequest {

    private Long topicId;
    private String title;
    private String content;
}
