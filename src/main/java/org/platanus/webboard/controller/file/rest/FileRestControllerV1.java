package org.platanus.webboard.controller.file.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.platanus.webboard.config.security.permission.HasAdminRole;
import org.platanus.webboard.config.security.permission.HasUserRole;
import org.platanus.webboard.controller.board.dto.ErrorDto;
import org.platanus.webboard.controller.file.FileService;
import org.platanus.webboard.controller.file.dto.FileDeleteDto;
import org.platanus.webboard.controller.file.dto.FileUploadDto;
import org.platanus.webboard.controller.user.UserService;
import org.platanus.webboard.domain.File;
import org.platanus.webboard.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class FileRestControllerV1 {

    private final UserService userService;
    private final FileService fileService;

    @PostMapping
    @ResponseBody
    @HasUserRole
    public ResponseEntity<?> uploadFile(@ModelAttribute FileUploadDto uploadDto,
                                        @AuthenticationPrincipal Object principal) {
        User user;
        List<File> uploadedFiles;
        try {
            user = userService.findByUsername(principal.toString());
            uploadDto.setUserId(user.getId());
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("")
                    .errorMessage(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        try {
            uploadedFiles = fileService.uploadFiles(uploadDto);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("파일 업로드 에러")
                    .errorMessage("파일 업로드에 실패 했습니다.")
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok().body(uploadedFiles);
    }

    @DeleteMapping("/{fileId}")
    @ResponseBody
    @HasUserRole
    public ResponseEntity<?> updateDeleteFlag(@PathVariable Long fileId,
                                              @AuthenticationPrincipal Object principal) {
        User user;
        try {
            user = userService.findByUsername(principal.toString());
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("")
                    .errorMessage(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        if (fileId < 1 || fileId == null) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("")
                    .errorMessage("파일 아이디가 없습니다.")
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        FileDeleteDto fileDto = FileDeleteDto.builder()
                .fileId(fileId)
                .userId(user.getId())
                .deleted(true)
                .build();
        try {
            fileService.updateDeleteFlagByUser(fileDto);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("")
                    .errorMessage("파일이 존재하지 않습니다.")
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok().body("200");
    }

    @DeleteMapping("/fileDeleteFromStorage/{fileId}")
    @ResponseBody
    @HasAdminRole
    public ResponseEntity<?> realFileDelete(@PathVariable Long fileId,
                                            @AuthenticationPrincipal Object principal) {
        User user;

        try {
            user = userService.findByUsername(principal.toString());
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("")
                    .errorMessage(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        if (fileId < 1 || fileId == null) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("")
                    .errorMessage("파일 아이디가 없습니다.")
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        FileDeleteDto fileDto = FileDeleteDto.builder()
                .fileId(fileId)
                .userId(user.getId())
                .deleted(true)
                .build();
        try {
            fileService.deleteFile(fileDto.getFileId());
//            fileService.updateDeleteFlag(fileDto);
        } catch (Exception e) {
            ErrorDto errorDto = ErrorDto.builder()
                    .errorId(999)
                    .errorCode("")
                    .errorMessage("파일이 존재하지 않습니다.")
                    .build();
            return ResponseEntity.badRequest().body(errorDto);
        }
        return ResponseEntity.ok().body("200");
    }
}
