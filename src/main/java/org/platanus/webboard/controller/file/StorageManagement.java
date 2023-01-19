package org.platanus.webboard.controller.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.constant.MessageConstant;
import org.platanus.webboard.config.property.PropertyEnvironment;
import org.platanus.webboard.controller.file.dto.FileDeleteDto;
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

    /**
     * Multipart 목록으로 부터 파일 추출하여 저장하는 메소드 <br />
     *
     * @param files 저장하고자 하는 파일 목록.
     * @return 저장 성공 시 storePathPrefix가 포함된 FileStoreDto 목록 반환.
     * @throws IOException
     */
    public List<FileStoreDto> storeFiles(List<MultipartFile> files) throws IOException {
        List<FileStoreDto> storeFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                storeFiles.add(storeFile(file));
            }
        }
        return storeFiles;
    }

    /**
     * 파일을 실제로 저장하는 메소드 <br />
     *
     * @param file
     * @return 저장 성공 시 storePathPrefix가 포함된 FileStoreDto 반환.
     * @throws IOException
     */
    private FileStoreDto storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String originalFilename = file.getOriginalFilename();
        String managementFilename = createManagementFilename(originalFilename);
        Path storeDirectoryPath = getUploadStoragePath();
        String storePathWithManagementFilename = getFileFullPath(storeDirectoryPath, managementFilename);
        // 사용자가 업로드한 원래 파일명에서 확장자를 분리하고 UUID 파일명을 생성.
        // 실제 저장할 경로 지정.
        FileStoreDto storeFile = FileStoreDto.builder()
                .originalFilename(originalFilename)
                .originalExtension(extractExtensionFromOriginalFile(originalFilename))
                .managementFilename(managementFilename)
                .storePathPrefix(getStoragePathPrefix().toString())
                .managementFilenameWithFullPath(storePathWithManagementFilename)
                .size(file.getSize())
                .build();
        // 지정된 경로에 디렉토리가 없으면 만드는 부분.
        File pathAsFile = new File(storeDirectoryPath.toString());
        if (!Files.exists(Paths.get(storeDirectoryPath.toString()))) {
            Files.createDirectories(storeDirectoryPath);
            //pathAsFile.mkdirs();
        }
        // 저장소에 파일 저장
        try {
            file.transferTo(new File(storeFile.getManagementFilenameWithFullPath()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info(MessageConstant.STORE_SUCCESS_LOG, storePathWithManagementFilename, file.getSize());
        return storeFile;
    }

    /**
     * 지정된 파일을 저장소에서 삭제한다.<br />
     *
     * @param fileDto managementFilename 와 storePathPrefix 가 표함된 DTO
     * @return 삭제 시 1, 그외 0
     */
    public int deleteFile(FileDeleteDto fileDto) {
        String fullPath = Paths.get(propertyEnvironment.getAttachFileStoragePath(),
                        fileDto.getStorePathPrefix(),
                        fileDto.getManagementFilename())
                .toString();
        File targetFile = new File(fullPath);
        if (targetFile.delete()) {
            return 1;
        }
        return 0;
    }

    /**
     * 저장 디렉토리 경로를 생성하는 메소드 <br />
     *
     * @return /{저장소경로}/{년}/{월}/{일}
     */
    private Path getUploadStoragePath() {
        return Paths.get(propertyEnvironment.getAttachFileStoragePath(),
                getStoragePathPrefix().toString());
    }

    /**
     * 저장 디렉토리 경로 중 날짜 구분 경로를 생성하는 메소드 <br />
     *
     * @return {년}/{월}/{일}
     */
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
