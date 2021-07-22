package org.platanus.webboard.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long articleId;
    private String content;
    private Long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;
}
