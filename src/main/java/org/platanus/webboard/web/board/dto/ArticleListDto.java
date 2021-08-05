package org.platanus.webboard.web.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleListDto {
    private long id;
    private long boardId;
    private String title;
    private long authorId;
    private String authorNickname;
    private LocalDateTime createdDate;
    private boolean deleted;
}
