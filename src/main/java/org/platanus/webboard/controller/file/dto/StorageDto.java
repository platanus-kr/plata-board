package org.platanus.webboard.controller.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StorageDto {
    private String originalFilename;
    private String originalExtension;
    private String managementFilename;
    private String fullPath;
}
