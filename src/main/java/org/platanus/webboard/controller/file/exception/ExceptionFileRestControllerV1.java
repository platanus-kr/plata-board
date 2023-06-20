package org.platanus.webboard.controller.file.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice("org.platanus.webboard.controller.file.rest")
public class ExceptionFileRestControllerV1 {
	
	@ExceptionHandler(FileException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 추후 BAD_REQUEST 와 분리 해야함.
	@ResponseBody
	public FileErrorDto fileServiceException(Exception e) {
		log.error("RestExceptionHandler", e);
		return new FileErrorDto(999L, "ERROR", e.getMessage());
	}
	
}
