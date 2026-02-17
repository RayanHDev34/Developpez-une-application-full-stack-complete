package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.payload.response.CommentResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMapper {

    public Comment toEntity(String content, User author, Article article) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setAuthor(author);
        comment.setArticle(article);
        return comment;
    }

    public CommentResponse toResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setContent(comment.getContent());
        response.setCreatedAt(comment.getCreatedAt());
        response.setAuthorName(comment.getAuthor().getUsername());
        return response;
    }

    public List<CommentResponse> toResponseList(List<Comment> comments) {
        return comments.stream()
                .map(this::toResponse)
                .toList();
    }
}
