package dev.devpool.controller;

import dev.devpool.dto.*;
import dev.devpool.dto.common.CommonDataListResponseDto;
import dev.devpool.dto.common.CommonDataResponseDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "memberPool", description = "memberPool")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberPoolController {
    private final MemberPoolService memberPoolService;

    @Operation(summary = "멤버 풀 등록", description = "멤버 풀을 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "멤버 풀을 성공적으로 저장하였습니다."),
            @ApiResponse(responseCode = "409", description = "멤버 풀 저장 실패 - 중복된 멤버가 있습니다."),
            @ApiResponse(responseCode = "500", description = "멤버 풀 저장 실패 - 인터넷 에러")
    })
    @PostMapping("/member-pool")
    public ResponseEntity<CommonResponseDto<Object>> saveMemberPool(@RequestBody @Valid MemberPoolDto.Save memberPoolDtoSave) {
        // 저장
        CommonResponseDto<Object> responseDto = memberPoolService.join(memberPoolDtoSave);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "멤버 풀 조회", description = "특정 멤버 풀을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 풀 조회 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 풀 조회 실패 - 멤버 풀이 DB에 없습니다.")
    })
    @GetMapping("/member-pool/{memberId}")
    public ResponseEntity<CommonDataResponseDto<Object>> findMemberPool(@PathVariable("memberId") Long memberId) {

        MemberPoolDto.Response memberPoolResponse = memberPoolService.findOneById(memberId);

        CommonDataResponseDto<Object> responseDto = CommonDataResponseDto.builder()
                .data(memberPoolResponse)
                .status(200)
                .message("멤버 풀 조회에 성공하였습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "멤버 풀 리스트 조회", description = "모든 멤버 풀의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 풀 멤버 풀 조회 - 성공"),
    })
    @GetMapping("/member-pools")
    public ResponseEntity<CommonDataListResponseDto<MemberPoolDto.Response>> findMemberPoolList() {
        List<MemberPoolDto.Response> memberPoolListDto = memberPoolService.findMemberPools();


        CommonDataListResponseDto<MemberPoolDto.Response> listResponseDto = CommonDataListResponseDto.<MemberPoolDto.Response>builder()
                .status(200)
                .message("멤버 풀 리스트 조회에 성공하였습니다.")
                .dataList(memberPoolListDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(listResponseDto);
    }

    @Operation(summary = "멤버 풀 정보 삭제", description = "멤버 풀의 정보를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 삭제 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 삭제 실패 - 멤버가 DB에 없습니다.")
    })
    @DeleteMapping("/member-pool/{memberId}")
    public ResponseEntity<CommonResponseDto<Object>> deleteMemberPool(@PathVariable("memberId") Long memberId) {
        CommonResponseDto<Object> responseDto = memberPoolService.deleteById(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "멤버 풀 정보 수정", description = "멤버 풀의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 풀 수정 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 풀 수정 - 실패")
    })
    @PutMapping("/member-pool")
    public ResponseEntity<CommonResponseDto<Object>> updateMemberPool(@RequestBody @Valid MemberPoolDto.Save memberPoolDto) {
        CommonResponseDto<Object> responseDto = memberPoolService.update(memberPoolDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
