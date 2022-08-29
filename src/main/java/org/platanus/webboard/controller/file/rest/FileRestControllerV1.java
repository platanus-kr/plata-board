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

/**
 * 파일 업로드를 위한 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class FileRestControllerV1 {

    private final UserService userService;
    private final FileService fileService;

    /**
     * 파일 업로드를 위한 컨트롤러.<br />
     * FileUploadDto 필드를 모두 만족해야 한다.<br />
     * ROLE_USER 권한 필수<br />
     *
     * @param uploadDto FileUploadDto
     * @param principal Spring Security
     * @return 업로드 성공 시 fileId가 포함된 File 반환
     */
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

    /**
     * 파일 삭제를 위한 컨트롤러. <br />
     * 저장소로부터 실제로 삭제는 하지 않고 DB에 deleted를 true로 업데이트 한다.<br />
     * 유저 인터페이스와 관련 있기 때문에 파일 소유자만 삭제 할 수 있다. <br />
     * ROLE_USER 권한 필수<br />
     *
     * @param fileId    삭제를 위한 파일ID (필수)
     * @param principal Spring Security
     * @return 파일 삭제 성공시 200 반환
     */
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

    /**
     * 파일을 저장소로부터 실제 삭제를 위한 컨트롤러 <br />
     * DB에서 deleted를 true로 변환하고, 파일을 저장소로부터 <b>실제로 삭제</b>한다. (주의) <br />
     * ROLE_ADMIN 필수 <br />
     *
     * @param fileId    삭제를 위한 파일ID (필수)
     * @param principal Spring Security
     * @return 파일 삭제 성공시 200 반환
     */
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
