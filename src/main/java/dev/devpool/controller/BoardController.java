package dev.devpool.controller;

import dev.devpool.dto.BoardDto;
import dev.devpool.dto.LatterDto;
import dev.devpool.dto.common.CommonDataListResponseDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.service.BoardCommentService;
import dev.devpool.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "board", description = "board")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    // 조회
    @Operation(summary = "게시판 전체 정보조회", description = "게시판의 전체 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 조회 - 성공"),
    })
    @GetMapping("/boards")
    public ResponseEntity<CommonDataListResponseDto<BoardDto.Response>> findAll() {

        List<BoardDto.Response> boardDtoList = boardService.findAll();

        CommonDataListResponseDto<BoardDto.Response> responseDto = CommonDataListResponseDto.<BoardDto.Response>builder()
                .status(200)
                .message("게시판 리스트 조회에 성공하였습니다.")
                .dataList(boardDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 저장
    @Operation(summary = "게시판 생성 ", description = "게시판을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 생성 - 성공"),
            @ApiResponse(responseCode = "404", description = "게시판 생성 - 실패")
    })
    @PostMapping("/board")
    public ResponseEntity<CommonResponseDto<Object>> saveBoard(@RequestBody BoardDto.Save boardDto) {
        CommonResponseDto<Object> responseDto = boardService.join(boardDto);


        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 삭제
    @Operation(summary = "게시판 삭제", description = "게시판을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 삭제 - 성공"),
            @ApiResponse(responseCode = "404", description = "게시판 삭제 - 실패")
    })
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<CommonResponseDto<Object>> deleteBoard(@PathVariable("boardId") Long boardId) {
        CommonResponseDto<Object> responseDto = boardService.deleteById(boardId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 수정
    @Operation(summary = "게시판 수정", description = "게시판을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시판 수정 - 성공"),
            @ApiResponse(responseCode = "404", description = "게시판 수정 - 실패")
    })
    @PatchMapping("/board")
    public ResponseEntity<CommonResponseDto<Object>> updateBoard(@RequestBody BoardDto.Update boardDto) {
        CommonResponseDto<Object> responseDto = boardService.update(boardDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
