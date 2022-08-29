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
    /**
     * 사용자가 업로드 한 원래 파일 이름 <br />
     * {파일명}.{확장자}
     */
    private String originalFilename;
    /**
     * 원래 파일의 확장자 <br />
     * {확장자}
     */
    private String originalExtension;
    /**
     * 관리 파일 이름 <br />
     * {UUID}.{확장자}
     */
    private String managementFilename;
    /**
     * 저장 경로 <br />
     * {년}/{월}/{일}
     */
    private String storePathPrefix;
    /**
     * 파일 크기<br />
     * {byte}
     */
    private Long size;
    /**
     * 원래 파일 이름과 경로가 합쳐진 최종 절대경로 <br />
     * {저장소경로}/{년}/{월}/{일}/{UUID}.{확장자}
     */
    private String managementFilenameWithFullPath;
    /**
     * 파일 만료 일자
     */
    private LocalDateTime expireDate;
}
