package org.platanus.webboard.controller.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.property.PropertyEnvironment;
import org.platanus.webboard.controller.file.dto.FileStoreDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageManagement {

    private final PropertyEnvironment propertyEnvironment;

    public List<FileStoreDto> storeFiles(List<MultipartFile> files) throws IOException {
        List<FileStoreDto> storeFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                storeFiles.add(storeFile(file));
            }
        }
        return storeFiles;
    }

    private FileStoreDto storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        String managementFilename = createManagementFilename(originalFilename);
        Path storeDirectoryPath = getUploadStoragePath();
        String storePathWithManagementFilename = getFileFullPath(storeDirectoryPath, managementFilename);
        FileStoreDto storeFile = FileStoreDto.builder()
                .originalFilename(originalFilename)
                .originalExtension(extractExtensionFromOriginalFile(originalFilename))
                .managementFilename(managementFilename)
                .storePathPrefix(getStoragePathPrefix().toString())
                .fullPath(storePathWithManagementFilename)
                .size(file.getSize())
                .build();
        // 디렉토리 없으면 만드는 부분
        File pathAsFile = new File(storeDirectoryPath.toString());
        if (!Files.exists(Paths.get(storeDirectoryPath.toString()))) {
            Files.createDirectories(storeDirectoryPath);
            //pathAsFile.mkdirs();
        }
        // Write.
        try {
            file.transferTo(new File(storeFile.getFullPath()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("Upload file : {} , filesize : {}", storePathWithManagementFilename, file.getSize());
        return storeFile;
    }

    private Path getUploadStoragePath() {
        return Paths.get(propertyEnvironment.getAttachFileStoragePath(),
                getStoragePathPrefix().toString());
    }

    private Path getStoragePathPrefix() {
        String todayYear = String.valueOf(LocalDateTime.now().getYear());
        String todayMonth = getStringByInt(LocalDateTime.now().getMonthValue());
        String todayDay = getStringByInt(LocalDateTime.now().getDayOfMonth());
        return Paths.get(todayYear, todayMonth, todayDay);
    }

    /**
     * 저장된 절대경로를 생성하는 메소드 <br />
     * 추출시 경로와 파일이름 끝까지 반환.<br />
     *
     * @param filename ManagementFilename 필수
     * @return /{저장경로}/{연도}/{월}/{일}/{UUID}.{확장자}
     */
    public String getFileFullPath(Path storePathPrefix, String filename) {
        return Paths.get(storePathPrefix.toString(),
                filename).toString();
    }

    /**
     * ManagementFilename 생성 <br />
     *
     * @param originalFilenameWithExtension 확장자가 포함된 원래 파일 이름
     * @return {UUID}.{확장자}
     */
    private String createManagementFilename(String originalFilenameWithExtension) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExtensionFromOriginalFile(originalFilenameWithExtension);
        return uuid + "." + ext;
    }

    /**
     * 확장자 분리기 <br />
     *
     * @param originalFilenameWithExtension 확장자가 포함된 원래 파일 이름
     * @return {확장자}
     */
    private String extractExtensionFromOriginalFile(String originalFilenameWithExtension) {
        int position = originalFilenameWithExtension.lastIndexOf(".");
        return originalFilenameWithExtension.substring(position + 1);
    }


    private static String getStringByInt(int number) {
        String numberToString;
        if (number < 10) {
            numberToString = "0" + number;
        } else {
            numberToString = String.valueOf(number);
        }
        return numberToString;
    }
}
