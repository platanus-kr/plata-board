package org.platanus.webboard.controller.board.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class ArticlesResponseDto {
    private long id;
    private long boardId;
    private String boardName;
    private Page<ArticleListDto> articles;
}
