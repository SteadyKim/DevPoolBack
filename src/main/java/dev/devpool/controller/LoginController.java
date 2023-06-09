package dev.devpool.controller;

import dev.devpool.dto.MemberDto;
import dev.devpool.exception.CustomException;
import dev.devpool.parameter.MemberParameter;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.dto.LoginDto;
import dev.devpool.jwt.TokenInfo;
import dev.devpool.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @Operation(summary = "로그인", description = "회원 로그인을 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "500", description = "로그인 실패")
    })
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
    @PostMapping(value = "/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponseDto<Object>> saveMember(@ParameterObject @ModelAttribute MemberParameter.Save memberParameter) throws IOException {




        // 저장
        CommonResponseDto<Object> responseDto = memberService.join(memberParameter);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDto);
    }
}
