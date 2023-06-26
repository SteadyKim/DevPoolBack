package dev.devpool.controller;

import dev.devpool.dto.BoardCommentDto;
import dev.devpool.dto.BoardDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.service.BoardCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "boardComment", description = "boardComment")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    // 게시글 저장
    @Operation(summary = "게시판 댓글 생성 ", description = "게시판에 댓글을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 댓글 생성 - 성공"),
            @ApiResponse(responseCode = "404", description = "게시판 댓글 생성 - 실패")
    })
    @PostMapping("/board-comment")
    public ResponseEntity<CommonResponseDto<Object>> saveBoardComment(@RequestBody BoardCommentDto.Save boardCommentDto) {

        CommonResponseDto<Object> responseDto = boardCommentService.join(boardCommentDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 삭제
    @Operation(summary = "게시판 댓글 삭제", description = "게시판 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 삭제 - 성공"),
            @ApiResponse(responseCode = "404", description = "게시판 삭제 - 실패")
    })
    @DeleteMapping("/board-comment/{boardCommentId}")
    public ResponseEntity<CommonResponseDto<Object>> deleteBoardComment(@PathVariable("boardCommentId") Long boardCommentId) {
        CommonResponseDto<Object> responseDto = boardCommentService.deleteById(boardCommentId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 수정
    @Operation(summary = "게시판 댓글 수정", description = "게시판을 댓글 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 댓글 수정 - 성공"),
            @ApiResponse(responseCode = "404", description = "게시판 댓글 수정 - 실패")
    })
    @PatchMapping("/board-comment")
    public ResponseEntity<CommonResponseDto<Object>> updateBoardComment(@RequestBody BoardCommentDto.Update boardCommentDto) {
        CommonResponseDto<Object> responseDto = boardCommentService.update(boardCommentDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
