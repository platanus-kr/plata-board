package org.platanus.webboard.web.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleWriteDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
