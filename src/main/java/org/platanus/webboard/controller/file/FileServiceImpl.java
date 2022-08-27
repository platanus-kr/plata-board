package org.platanus.webboard.controller.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.file.dto.FileStoreDto;
import org.platanus.webboard.controller.file.dto.FileUploadDto;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.File;
import org.platanus.webboard.domain.FileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final StorageManagement storageManagement;
    private final UserService userService;

    @Override
    public List<File> uploadFiles(FileUploadDto attachDto) throws IOException {
        if (attachDto.getUserId() < 1) {
            return null;
        }
        try {
            userService.findById(attachDto.getUserId());
        } catch (Exception e) {
            return null;
        }
        List<File> uploadedFiles = new ArrayList<>();
        // 파일시스템에 파일 저장.
        List<FileStoreDto> attachFiles = storageManagement.storeFiles(attachDto.getFiles());
        // DB에 파일 저장 정보 기록.
        for (FileStoreDto attachFile : attachFiles) {
            File uploadedFile = File.builder()
                    .userId(attachDto.getUserId())
                    .originalFilename(attachFile.getOriginalFilename())
                    .originalExtension(attachFile.getOriginalExtension())
                    .managementFilename(attachFile.getManagementFilename())
                    .storePathPrefix(attachFile.getStorePathPrefix())
                    .size(attachFile.getSize())
                    .deleted(false)
                    .expireDate(LocalDateTime.parse(attachDto.getExpireDate()))
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            uploadedFiles.add(uploadedFile);
            fileRepository.upload(uploadedFile);
        }
        return uploadedFiles;
    }

}
