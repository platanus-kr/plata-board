package org.platanus.webboard.dto;

import lombok.Data;

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
}
