package org.platanus.webboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class File {
    private Long id;
    private Long userId;
    private String originalFilename;
    private String originalExtension;
    private String managementFilename;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Boolean deleted;
    private LocalDateTime expireDate;
}
