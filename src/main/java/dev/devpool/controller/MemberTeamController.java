package dev.devpool.controller;

import dev.devpool.dto.TeamDto;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.parameter.MemberTeamParameter;
import dev.devpool.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberTeamController {

    private final MemberTeamService memberTeamService;

    @Operation(summary = "팀에 멤버 등록", description = "팀에 멤버를 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "팀에 멤버를 성공적으로 저장하였습니다."),
            @ApiResponse(responseCode = "409", description = "팀에 멤버 저장 실패"),
    })
    @PostMapping("/memberTeam")
    public ResponseEntity<CommonResponseDto<Object>> saveMemberTeam(@ModelAttribute MemberTeamParameter memberTeamParameter) {

        CommonResponseDto<Object> responseDto = memberTeamService.join(memberTeamParameter.getMemberId(), memberTeamParameter.getTeamId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


    @Operation(summary = "팀에서 멤버 삭제", description = "팀에서 멤버를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "팀에 멤버를 성공적으로 삭제하였습니다."),
            @ApiResponse(responseCode = "409", description = "팀에 멤버 삭제 실패"),
    })
    @DeleteMapping("/memberTeam")
    public ResponseEntity<CommonResponseDto<Object>> deleteMemberTeam(@ModelAttribute MemberTeamParameter memberTeamParameter) {

        CommonResponseDto<Object> responseDto = memberTeamService.delete(memberTeamParameter.getMemberId(), memberTeamParameter.getTeamId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
