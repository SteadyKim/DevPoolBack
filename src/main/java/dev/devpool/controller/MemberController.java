package dev.devpool.controller;

import dev.devpool.domain.Member;
import dev.devpool.dto.*;
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
import java.util.ArrayList;
import java.util.List;

@Tag(name = "member", description = "member")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원을 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원을 성공적으로 저장하였습니다."),
            @ApiResponse(responseCode = "409", description = "멤버 저장 실패 - 중복된 멤버가 있습니다."),
            @ApiResponse(responseCode = "500", description = "멤버 저장 실패 - 인터넷 에러")
    })
    @PostMapping("/member")
    public ResponseEntity<CommonResponseDto<Object>> saveMember(@RequestBody @Valid MemberDto.Save memberSaveRequestDto) {
        // 저장
        Member member = memberSaveRequestDto.toEntity();
        Long id = memberService.join(member);


        // 응답
        CommonResponseDto<Object> createMemberResponse = CommonResponseDto.builder()
                .id(member.getId())
                .status(201)
                .message("회원 저장에 성공하였습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createMemberResponse);
    }

    @Operation(summary = "회원정보조회", description = "본인의 회원정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 조회 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 조회 실패 - 멤버가 DB에 없습니다.")
    })
    @GetMapping("/member/{id}")
    public ResponseEntity<CommonDataResponseDto<Object>> getMember(@PathVariable("id") Long id) {
        Member findMember = memberService.findOneById(id);
        MemberDto.Response memberDto = findMember.toDto();

        CommonDataResponseDto<Object> responseDto = CommonDataResponseDto.builder()
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
    public ResponseEntity<CommonDataListResponseDto<Object>> getMemberList() {
        List<Member> memberList = memberService.findAll();
        ArrayList<Object> memberDtoList = new ArrayList<Object>();

        for (Member member : memberList) {
            MemberDto.Response memberDto = member.toDto();
            memberDtoList.add(memberDto);
        }


        CommonDataListResponseDto<Object> listResponseDto = CommonDataListResponseDto.builder()
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
    @DeleteMapping("/member/{id}")
    public ResponseEntity<CommonResponseDto<Object>> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        CommonResponseDto<Object> responseDto = CommonResponseDto.builder()
                .status(200)
                .message("멤버 삭제에 성공하였습니다.")
                .id(id)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "회원정보수정", description = "회원의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버 수정 - 성공"),
            @ApiResponse(responseCode = "404", description = "멤버 수정 실패")
    })
    @PatchMapping("/member/{id}")
    public ResponseEntity<CommonResponseDto<Object>> updateMember(@PathVariable("id") Long id, @RequestBody @Valid MemberDto.Save memberDto) {
        Member updateMember = memberService.update(id, memberDto.toEntity());

        CommonResponseDto<Object> responseDto = CommonResponseDto.builder()
                .status(200)
                .message("멤버 수정에 성공하였습니다.")
                .id(updateMember.getId())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


}
