package org.platanus.webboard.controller.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FileUploadRequestDto {
    /**
     * 파일을 업로드 유저, 소유자.
     */
    private Long userId;
    /**
     * Multipart 객체 목록
     */
    private List<MultipartFile> files;
    /**
     * 파일 만료 일자<br />
     * String으로 먼저 받아서 LocalDateTime으로 convert한다.
     */
    private String expireDate;
    /**
     * 파일 삭제 여부
     */
    private Boolean deleted;
}
