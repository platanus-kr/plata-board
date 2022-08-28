package org.platanus.webboard.controller.file.dto;

import lombok.Builder;
import lombok.Data;
import org.platanus.webboard.domain.File;

/**
 * 파일 업로드 후 경로를 포함한 파일 이름 전달에 필요한 DTO
 */
@Data
@Builder
public class FileDownloadDto {
    private String managementFilename;
    private String originalFilename;
    private String storePathPrefix;
    private String originalFilenameWithFullPath;

    public static FileDownloadDto fromFile(File file) {
        return FileDownloadDto.builder()
                .managementFilename(file.getManagementFilename())
                .originalFilename(file.getOriginalFilename())
                .storePathPrefix(file.getStorePathPrefix())
                .build();
    }
}
