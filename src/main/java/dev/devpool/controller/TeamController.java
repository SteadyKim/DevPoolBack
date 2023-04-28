package dev.devpool.controller;


import dev.devpool.domain.*;
import dev.devpool.dto.CreateMemberDto;
import dev.devpool.dto.CreateTeamDto;
import dev.devpool.service.MemberService;
import dev.devpool.service.StackService;
import dev.devpool.service.TeamService;
import dev.devpool.service.TechFieldService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final MemberService memberService;
    private final TechFieldService techFieldService;
    private final StackService stackService;


    @Operation(summary = "팀등록", description = "팀을 저장합니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "팀을 성공적으로 저장하였습니다."),
            @ApiResponse(code = 409, message = "팀 저장 실패 - 중복된 팀이름이 있습니다."),
    })
    @PostMapping()
    public ResponseEntity<CreateTeamResponse> saveTeam(@RequestBody @Valid CreateTeamDto createTeamDto) {
        // 저장
        // 팀, 팀멤버, 멤버

        Team team = createTeamDto.toEntity();

        Long memberId = createTeamDto.getMemberId();
        Member findMember = memberService.findOneById(memberId);
        MemberTeam memberTeam = new MemberTeam();
        memberTeam.addMemberTeam(findMember, team);
        teamService.join(team);

        // 스택
        List<String> stackNameList = createTeamDto.getStackNameList();
        for (String stackName : stackNameList) {
            Stack stack = new Stack();
            stack.setName(stackName);
            stack.setTeam(team);
            stackService.join(stack);
        }


        // TechField
        List<String> techFieldNameList = createTeamDto.getTechFieldNameList();
        for (String techFieldName : techFieldNameList) {
            TechField techField = new TechField();
            techField.setName(techFieldName);
            techField.setTeam(team);
            techFieldService.join(techField);
        }


        CreateTeamResponse createTeamResponse = CreateTeamResponse.builder()
                .status(201)
                .message("팀 저장에 성공하였습니다.")
                .id(team.getId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createTeamResponse);
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class CreateTeamResponse {
        private int status;
        private String message;
        private Long id;
    }
}
