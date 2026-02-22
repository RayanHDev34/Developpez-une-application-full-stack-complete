package com.openclassrooms.mddapi.payload.response;

import com.openclassrooms.mddapi.dto.ArticleDto;

import java.util.List;

public class ArticleDetailResponse {

    private ArticleDto article;
    private List<CommentResponse> comments;

    public ArticleDetailResponse(ArticleDto article, List<CommentResponse> comments) {
        this.article = article;
        this.comments = comments;
    }

    public ArticleDto getArticle() {
        return article;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }
}