package org.platanus.webboard.controller.file;

import org.platanus.webboard.controller.file.dto.FileDownloadDto;
import org.platanus.webboard.controller.file.dto.FileUploadDto;
import org.platanus.webboard.domain.File;

import java.io.IOException;
import java.util.List;

public interface FileService {
    List<File> uploadFiles(FileUploadDto attachFiles) throws IOException;

    FileDownloadDto findById(Long fileId);

    String getStoreFullPathByFilename(String managementFilename);
}
