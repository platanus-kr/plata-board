package org.platanus.webboard.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File {
    /* 파일을 구분 짓는 유니크 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 파일을 업로드 유저, 소유자.
     */
    private Long userId;
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
     * 파일 생성(업로드) 일자
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createDate;
    /**
     * 파일 레코드 수정 일자 <br />
     * ex) 파일 삭제
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateDate;
    /**
     * 파일 삭제 여부<br />
     * 이 삭제 여부를 가지고 배치를 통해 실제 파일 삭제.
     */
    private Boolean deleted;
    /**
     * 파일 만료 일자 <br />
     * 만료 일자를 가지고 배치를 통해 실제 파일 삭제.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expireDate;
}
