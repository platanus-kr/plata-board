package org.platanus.webboard.controller.board.exception;


import lombok.Getter;

@Getter
public class BoardException extends RuntimeException {
    private String message;

    public BoardException(String message) {
        this.message = message;
    }
}
