package org.platanus.webboard.controller.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FileUploadDto {
    private Long userId;
    private List<MultipartFile> files;
    private String expireDate;
    private Boolean deleted;
}
