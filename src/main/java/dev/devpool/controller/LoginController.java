package dev.devpool.controller;

import dev.devpool.dto.CommonResponseDto;
import dev.devpool.dto.LoginDto;
import dev.devpool.dto.MemberDto;
import dev.devpool.dto.MemberTokenDto;
import dev.devpool.jwt.TokenInfo;
import dev.devpool.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        TokenInfo tokenInfo = memberService.login(email, password);

        return  tokenInfo;
    }

    @Operation(summary = "회원가입", description = "회원을 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원을 성공적으로 저장하였습니다."),
            @ApiResponse(responseCode = "409", description = "멤버 저장 실패 - 중복된 멤버가 있습니다."),
            @ApiResponse(responseCode = "500", description = "멤버 저장 실패 - 인터넷 에러")
    })
    @PostMapping("/join")
    public ResponseEntity<CommonResponseDto<Object>> saveMember(@RequestBody @Valid MemberDto.Save memberSaveRequestDto) {
        // 저장
        CommonResponseDto<Object> responseDto = memberService.join(memberSaveRequestDto);



        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }
}