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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "member", description = "member")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TeamService teamService;

    @Operation(summary = "회원정보조회", description = "본인의 회원정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 조회 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 조회 실패 - 멤버가 DB에 없습니다.")
    })
    @GetMapping("/member/{memberId}")
    public ResponseEntity<CommonDataResponseDto<MemberDto.Response>> findMember(@PathVariable("memberId") Long id) {
        MemberDto.Response memberDto = memberService.findOneById(id);

        CommonDataResponseDto<MemberDto.Response> responseDto = CommonDataResponseDto.<MemberDto.Response>builder()
                .data(memberDto)
                .status(200)
                .message("멤버 조회에 성공하였습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "회원정보리스트조회", description = "모든 회원의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 조회 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 조회 실패 - 멤버가 DB에 없습니다.")
    })
    @GetMapping("/members")
    public ResponseEntity<CommonDataListResponseDto<MemberDto.Response>> findMemberList() {
        List<MemberDto.Response> memberDtoList = memberService.findAll();



        CommonDataListResponseDto<MemberDto.Response> listResponseDto = CommonDataListResponseDto.<MemberDto.Response>builder()
                .status(200)
                .message("멤버 리스트 조회에 성공하였습니다.")
                .dataList(memberDtoList)
                .build();


        return ResponseEntity.status(HttpStatus.OK).body(listResponseDto);
    }

    @Operation(summary = "회원정보삭제", description = "회원의 정보를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 삭제 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 삭제 실패 - 멤버가 DB에 없습니다.")
    })
    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<CommonResponseDto<Object>> deleteMember(@PathVariable("memberId") Long id) {

        /**
         * 있는 경우에만 delete 해야하는데 그 비즈니스로직을 어떻게..?? 그리고 throw exception을 언젠 하지않고 언젠 해도 되는지
         */
        teamService.deleteByHostId(id);
        CommonResponseDto<Object> responseDto = memberService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "회원정보수정", description = "회원의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 수정 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 수정 - 실패")
    })
    @PatchMapping("/member/{memberId}")
    public ResponseEntity<CommonResponseDto<Object>> updateMember(@PathVariable("memberId") Long id, @RequestBody @Valid MemberDto.Save memberDto, @RequestParam(value = "image") MultipartFile image) throws IOException {
        CommonResponseDto<Object> responseDto = memberService.update(id, memberDto, image);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}
