package org.platanus.webboard.controller.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class FileStoreDto {
    private String originalFilename;
    private String originalExtension;
    private String managementFilename;
    private String storePathPrefix;
    private Long size;
    private String fullPath;
    private LocalDateTime expireDate;
}
