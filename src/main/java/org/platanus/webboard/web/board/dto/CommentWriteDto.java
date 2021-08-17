package org.platanus.webboard.web.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentWriteDto {
    @NotBlank
    private String content;
}
