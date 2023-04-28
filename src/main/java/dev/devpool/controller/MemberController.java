package dev.devpool.controller;

import dev.devpool.domain.Member;
import dev.devpool.dto.CreateMemberDto;
import dev.devpool.dto.MemberDto;
import dev.devpool.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원을 저장합니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "회원을 성공적으로 저장하였습니다."),
            @ApiResponse(code = 409, message = "멤버 저장 실패 - 중복된 멤버가 있습니다."),
            @ApiResponse(code = 500, message = "멤버 저장 실패 - 인터넷 에러")
    })
    @PostMapping()
    public ResponseEntity<CreateMemberResponse> saveMember(@RequestBody @Valid CreateMemberDto createMemberDto) {
        // 저장
        Member member = createMemberDto.toEntity();
        Long id = memberService.join(member);
        
        
        // 응답
        CreateMemberResponse createMemberResponse = CreateMemberResponse.builder()
                .id(member.getId())
                .status(201)
                .message("회원 저장에 성공하였습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createMemberResponse);
    }

    @Operation(summary = "회원정보조회", description = "본인의 회원정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "멤버 조회 - 성공"),
            @ApiResponse(code = 404, message = "멤버 조회 실패 - 멤버가 DB에 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetMemberResponse> getMember(@PathVariable("id") Long id) {
        Member findMember = memberService.findOneById(id);
        MemberDto memberDto = MemberDto.convertToMemberDto(findMember);

        GetMemberResponse getMemberResponse = GetMemberResponse.builder()
                .status(200)
                .message("멤버 조회에 성공하였습니다.")
                .memberDto(memberDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(getMemberResponse);
    }

    @Operation(summary = "회원정보삭제", description = "회원의 정보를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "멤버 삭제 - 성공"),
            @ApiResponse(code = 404, message = "멤버 삭제 실패 - 멤버가 DB에 없습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteMemberResponse> deleteMember(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        DeleteMemberResponse deleteMemberResponse = DeleteMemberResponse.builder()
                .status(200)
                .message("멤버 삭제에 성공하였습니다.")
                .id(id)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(deleteMemberResponse);
    }

    @Operation(summary = "회원정보수정", description = "회원의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "멤버 수정 - 성공"),
            @ApiResponse(code = 404, message = "멤버 수정 실패")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UpdateMemberResponse> updateMember(@PathVariable("id") Long id, @RequestBody @Valid MemberDto memberDto) {
        Member updateMember = memberService.update(id, memberDto.toEntity());

        MemberDto updateMemberDto = MemberDto.convertToMemberDto(updateMember);

        UpdateMemberResponse updateMemberResponse = UpdateMemberResponse.builder()
                .status(200)
                .message("멤버 수정에 성공하였습니다.")
                .memberDto(updateMemberDto)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(updateMemberResponse);
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class CreateMemberResponse {
        private int status;
        private String message;
        private Long id;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class GetMemberResponse {
        private int status;
        private String message;
        private MemberDto memberDto;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class DeleteMemberResponse {
        private int status;
        private String message;
        private Long id;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class UpdateMemberResponse {
        private int status;
        private String message;
        private MemberDto memberDto;
    }

}
