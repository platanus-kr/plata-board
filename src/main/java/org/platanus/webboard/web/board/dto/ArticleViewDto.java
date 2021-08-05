package org.platanus.webboard.web.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleViewDto {
    public long id;
    public long boardId;
    public String title;
    public String content;
    public long authorId;
    public String authorNickname;
    public LocalDateTime createdDate;
    public LocalDateTime modifiedDate;
}
