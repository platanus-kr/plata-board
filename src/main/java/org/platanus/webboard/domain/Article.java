package org.platanus.webboard.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class Article {
    private long id;
    private long boardId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
    private long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;
    private long recommend;
    private long viewCount;
}
