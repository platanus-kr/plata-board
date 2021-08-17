package org.platanus.webboard.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class Comment {
    private long id;
    private long articleId;

    @NotBlank
    private String content;
    private long authorId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean deleted;
}
