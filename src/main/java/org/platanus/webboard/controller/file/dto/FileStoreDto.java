package org.platanus.webboard.controller.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 파일 저장을 위한 DTO
 */
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
