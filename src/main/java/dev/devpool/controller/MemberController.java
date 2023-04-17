package dev.devpool.controller;

import dev.devpool.domain.Member;
import dev.devpool.dto.CreateMemberDto;
import dev.devpool.dto.MemberDto;
import dev.devpool.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name="회원", description = "회원 Controller")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원을 저장합니다.")
    @PostMapping()
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberDto createMemberDto) {

        Member member = createMemberDto.toEntity();
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @Operation(summary = "회원정보조회", description = "본인의 회원정보를 조회합니다.")
    @GetMapping("{id}")
    public MemberDto getMember(@PathVariable("id") Long id) {
        Member findMember = memberService.findOneById(id);

        MemberDto memberDto = MemberDto.convertToMemberDto(findMember);
        return memberDto;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }
}
