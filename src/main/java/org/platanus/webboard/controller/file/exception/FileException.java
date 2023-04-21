package org.platanus.webboard.controller.file.exception;


import lombok.Getter;

@Getter
public class FileException extends RuntimeException {
	private String message;
	
	public FileException(String message) {
		this.message = message;
	}
}
