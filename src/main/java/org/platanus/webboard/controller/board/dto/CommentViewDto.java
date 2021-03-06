package org.platanus.webboard.controller.board.dto;

import lombok.Data;
import org.platanus.webboard.domain.Comment;

import java.time.LocalDateTime;

@Data
public class CommentViewDto {
    private long id;
    private long articleId;
    private String content;
    private long authorId;
    private String authorNickname;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static CommentViewDto from(Comment c, String authorNickname) {
        CommentViewDto commentResponse = new CommentViewDto();
        commentResponse.setId(c.getId());
        commentResponse.setArticleId(c.getArticleId());
        commentResponse.setContent(c.getContent());
        commentResponse.setAuthorId(c.getAuthorId());
        commentResponse.setAuthorNickname(authorNickname);
        commentResponse.setCreatedDate(c.getCreatedDate());
        commentResponse.setModifiedDate(c.getModifiedDate());
        return commentResponse;
    }
}
