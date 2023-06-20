package org.platanus.webboard.controller.file;

import org.platanus.webboard.controller.file.dto.FileDeleteDto;
import org.platanus.webboard.controller.file.dto.FileDownloadDto;
import org.platanus.webboard.controller.file.dto.FileUploadRequestDto;
import org.platanus.webboard.domain.File;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<File> uploadFiles(FileUploadRequestDto attachFiles) throws IOException;

    FileDownloadDto findById(Long fileId);

    FileDeleteDto updateDeleteFlagByUser(FileDeleteDto fileDto) throws Exception;

    int deleteFile(Long fileId);

    FileDeleteDto deleteFilesByUser(Long userId);

    String getStoreFullPathByFilename(String managementFilename);
}
