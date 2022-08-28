package org.platanus.webboard.controller.file.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 파일 삭제를 위한 DTO
 */
@Data
@Builder
public class FileDeleteDto {
    private Long fileId;
    private Long userId;
    private String managementFilename;
    private String storePathPrefix;
    private Boolean deleted;
}
