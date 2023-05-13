package dev.devpool.controller;


import dev.devpool.domain.*;
import dev.devpool.dto.*;
import dev.devpool.service.*;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final MemberService memberService;
    private final TechFieldService techFieldService;
    private final StackService stackService;

    private final CategoryService categoryService;

    @Operation(summary = "팀등록", description = "팀을 저장합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "팀을 성공적으로 저장하였습니다."),
            @ApiResponse(responseCode = "409", description = "팀 저장 실패 - 중복된 팀이름이 있습니다."),
    })
    @PostMapping("/team")
    public ResponseEntity<CommonResponseDto<Object>> saveTeam(@RequestBody @Valid TeamDto.Save teamSaveRequestDto ) {
        // 저장
        // 팀, 팀멤버, stack, techfield 등 관련 모든 정보 저장
        CommonResponseDto<Object> responseDto = teamService.join(teamSaveRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 조회
    @Operation(summary = "팀조회", description = "팀을 팀 id로 조회 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀을 성공적으로 조회 하였습니다."),
            @ApiResponse(responseCode = "404", description = "팀 조회 실패 - 팀이 DB에 없습니다."),
    })
    @GetMapping("/team/{teamId}")
    public ResponseEntity<CommonDataResponseDto<TeamDto.Response>> findTeamById(@PathVariable("teamId") Long teamId) {
        // 조회
        // 팀 조회
        TeamDto.Response responseDto = teamService.findOneById(teamId);

        CommonDataResponseDto<TeamDto.Response> response = CommonDataResponseDto
                .<TeamDto.Response>builder()
                .status(200)
                .message("팀 조회에 성공하였습니다.")
                .data(responseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //
    @Operation(summary = "팀 모두 조회", description = "팀을 모두 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀 List를 성공적으로 조회하였습니다.")
    })
    @GetMapping("/teams")
    public ResponseEntity<CommonDataListResponseDto<TeamDto.Response>> findTeamList() {

        List<TeamDto.Response> responseDtoList = teamService.findAll();

        CommonDataListResponseDto<TeamDto.Response> response = CommonDataListResponseDto.<TeamDto.Response>builder()
                .status(200)
                .message("팀 리스트 조회에 성공하였습니다.")
                .dataList(responseDtoList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "팀 삭제", description = "팀을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀 삭제에 성공하였습니다.")
    })
    @DeleteMapping("/team/{teamId}")
    public ResponseEntity<CommonResponseDto<Object>> deleteTeam(@PathVariable("teamId") Long id) {

        CommonResponseDto<Object> response = teamService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "팀 수정", description = "팀을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀 수정에 성공하였습니다.")
    })
    @PutMapping("/team/{teamId}")
    public ResponseEntity<CommonResponseDto<Object>> updateTeam(@PathVariable("teamId") Long teamId, @RequestBody @Valid TeamDto.Update newTeamDto) {

        CommonResponseDto<Object> response = teamService.update(teamId, newTeamDto);


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
