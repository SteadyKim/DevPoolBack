package dev.devpool.controller;

import dev.devpool.dto.LatterDto;
import dev.devpool.dto.common.CommonDataListResponseDto;
import dev.devpool.service.LatterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "member", description = "member")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LatterController {

    private final LatterService latterService;

    @Operation(summary = "회원의 쪽지 정보조회", description = "회원의 쪽지 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 쪽지 조회 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 쪽지 조회 실패 - 멤버가 DB에 없습니다.")
    })
    @GetMapping("/latter/{senderId}")
    public ResponseEntity<CommonDataListResponseDto<List<LatterDto.Response>>> findLattersBySender(@PathVariable("senderId") Long senderId) {

        List<List<LatterDto.Response>> latterDtoList = latterService.findAllByMemberId(senderId);


        CommonDataListResponseDto<List<LatterDto.Response>> responseDto = CommonDataListResponseDto.<List<LatterDto.Response>>builder()
                .status(200)
                .message("멤버 쪽지 리스트 조회에 성공하였습니다.")
                .dataList(latterDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}