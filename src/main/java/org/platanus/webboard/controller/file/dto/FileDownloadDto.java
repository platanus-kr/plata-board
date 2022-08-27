package org.platanus.webboard.controller.file.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 파일 업로드 후 경로를 포함한 파일 이름 전달에 필요한 DTO
 */
@Data
@Builder
public class FileDownloadDto {
    private String managementFilenameWithRelativePath;
    private String managementFilenameWithWebAddress;
    private String originalFilenameAndExtension;
}
