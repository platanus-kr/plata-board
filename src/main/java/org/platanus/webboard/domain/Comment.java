package org.platanus.webboard.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private long id;
    private long articleId;
    private String content;
    private long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;
}
