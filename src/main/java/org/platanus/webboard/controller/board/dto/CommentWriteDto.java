package org.platanus.webboard.controller.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentWriteDto {
    @NotBlank
    private String content;
}
