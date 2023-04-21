package org.platanus.webboard.controller.file.dto;


import java.util.List;

import org.platanus.webboard.domain.File;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class FileUploadResponseDto {
	
	private List<File> uploadedFiles;
	
}
