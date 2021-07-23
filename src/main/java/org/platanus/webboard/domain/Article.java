package org.platanus.webboard.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private long id;
    private long boardId;
    private String content;
    private long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;

}
