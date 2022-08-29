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
    /**
     * 관리 파일 이름 <br />
     * {UUID}.{확장자}
     */
    private String managementFilename;
    /**
     * 사용자가 업로드 한 원래 파일 이름 <br />
     * {파일명}.{확장자}
     */
    private String originalFilename;
    /**
     * 저장 경로 <br />
     * {년}/{월}/{일}
     */
    private String storePathPrefix;
    /**
     * 원래 파일 이름과 경로가 합쳐진 최종 절대경로 <br />
     * {저장소경로}/{년}/{월}/{일}/{UUID}.{확장자}
     */
    private String managementFilenameWithFullPath;

    public static FileDownloadDto fromFile(File file) {
        return FileDownloadDto.builder()
                .managementFilename(file.getManagementFilename())
                .originalFilename(file.getOriginalFilename())
                .storePathPrefix(file.getStorePathPrefix())
                .build();
    }
}
