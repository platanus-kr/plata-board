package org.platanus.webboard.controller.board.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("org.platanus.webboard.controller.board.rest")
public class ExceptionBoardRestControllerV1 {

    @ExceptionHandler(BoardException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 추후 BAD_REQUEST 와 분리 해야함.
    @ResponseBody
    public ErrorDto boardServiceException(Exception e) {
        log.error("RestExceptionHandler", e);
        return new ErrorDto(999L, "ERROR", e.getMessage());
    }

}
