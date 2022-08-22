package org.platanus.webboard.controller.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FileUploadDto {
    private Long userId;
    private List<MultipartFile> files;
    private LocalDateTime expireDate;
    private Boolean deleted;
}
