package org.platanus.webboard.controller.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private long errorId;
    private String errorCode;
    private String errorMessage;
}
