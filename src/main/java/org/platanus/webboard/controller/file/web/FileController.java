package org.platanus.webboard.controller.file.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.controller.file.FileService;
import org.platanus.webboard.controller.file.dto.FileDownloadDto;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

/**
 * 파일 다운로드를 위한 웹 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/media")
public class FileController {
    private final FileService fileService;

    @GetMapping("/attach/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        if (fileId < 1) {
            return null;
        }
        FileDownloadDto fileDto = fileService.findById(fileId);
        UrlResource resource;
        try {
            resource = new UrlResource("file:" + fileDto.getOriginalFilenameWithFullPath());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        String encodedOriginalFileName = UriUtils.encode(fileDto.getOriginalFilename(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @GetMapping("/image/{*managementFilenameWithStorePathPrefix}")
    @ResponseBody
    public Resource downloadImage(@PathVariable String managementFilenameWithStorePathPrefix) {
        String storeFullPathByFilename = fileService.getStoreFullPathByFilename(managementFilenameWithStorePathPrefix);
        if (storeFullPathByFilename == null) {
            return null;
        }
        try {
            return new UrlResource("file:" + storeFullPathByFilename);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
