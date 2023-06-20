package org.platanus.webboard.controller.file.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 파일 삭제를 위한 DTO
 */
@Data
@Builder
public class FileDeleteDto {
    /**
     * 파일을 구분짓는 유니크
     */
    private Long fileId;
    /**
     * 파일을 업로드 유저, 소유자.
     */
    private Long userId;
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
     * 파일 삭제 여부
     */
    private Boolean deleted;
}
