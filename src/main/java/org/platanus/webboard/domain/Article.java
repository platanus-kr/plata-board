package org.platanus.webboard.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private Long id;
    private Long boardId;
    private String content;
    private Long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;
    
}
