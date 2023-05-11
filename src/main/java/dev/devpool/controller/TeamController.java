package dev.devpool.controller;


import dev.devpool.domain.*;
import dev.devpool.dto.CommonDataListResponseDto;
import dev.devpool.dto.CommonDataResponseDto;
import dev.devpool.dto.CommonResponseDto;
import dev.devpool.dto.TeamDto;
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
import java.util.ArrayList;
import java.util.Comparator;
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
        // 팀, 팀멤버, 멤버
        Team team = teamSaveRequestDto.toEntity();

        Long memberId = teamSaveRequestDto.getMemberId();
        Member findMember = memberService.findOneById(memberId);

        MemberTeam memberTeam = new MemberTeam();
        memberTeam.addMemberTeam(findMember, team);

        teamService.join(team);

        // 스택
        List<String> stackNameList = teamSaveRequestDto.getRecruitStackNameList();
        for (String stackName : stackNameList) {
            Stack stack = Stack.builder()
                    .name(stackName)
                    .team(team)
                    .build();

            stackService.join(stack);
        }

        // TechField
        List<String> techFieldNameList = teamSaveRequestDto.getRecruitTechFieldNameList();
        for (String techFieldName : techFieldNameList) {
            TechField techField = TechField.builder()
                    .name(techFieldName)
                    .team(team)
                    .build();
            techFieldService.join(techField);
        }

        // Category
        String categoryName = teamSaveRequestDto.getCategoryName();
        Category category = Category.builder()
                .name(categoryName)
                .team(team)
                .build();
        categoryService.join(category);

        CommonResponseDto<Object> createTeamResponse = CommonResponseDto.builder()
                .status(201)
                .message("팀 저장에 성공하였습니다.")
                .id(team.getId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createTeamResponse);
    }

    // 조회
    @Operation(summary = "팀조회", description = "팀을 팀 id로 조회 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀을 성공적으로 조회 하였습니다."),
            @ApiResponse(responseCode = "404", description = "팀 조회 실패 - 팀이 DB에 없습니다."),
    })
    @GetMapping("/team/{id}")
    public ResponseEntity<CommonDataResponseDto<Object>> getTeamById(@PathVariable("id") Long teamId) {
        // 조회
        // 팀 조회
        Team findTeam = teamService.findOneById(teamId);

        // 스택
        List<Stack> stackList = stackService.findAllByTeamId(teamId);

        // TechField
        List<TechField> techFieldList = techFieldService.findAllByTeamId(teamId);

        // Category
        Category category = categoryService.findOneByTeamId(teamId);

        TeamDto.Response teamResponseDto = findTeam.toDto(stackList, techFieldList, category);
        CommonDataResponseDto<Object> response = CommonDataResponseDto
                .builder()
                .status(200)
                .message("팀 조회에 성공하였습니다.")
                .data(teamResponseDto)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //
    @Operation(summary = "팀 모두 조회", description = "팀을 모두 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀 List를 성공적으로 조회하였습니다.")
    })
    @GetMapping("/teams")
    public ResponseEntity<CommonDataListResponseDto<Object>> getTeamList() {
        ArrayList<Object> responseList = new ArrayList<>();

        List<Team> teamList = teamService.findAll();

        teamList.sort(Comparator.comparing(Team::getCreateTime));

        for (Team team : teamList) {
            // 스택 가져오기
            List<Stack> stackList = stackService.findAllByTeamId(team.getId());

            //Tech Field 가져오기
            List<TechField> techFieldList = techFieldService.findAllByTeamId(team.getId());
            //category 가져오기
            Category category = categoryService.findOneByTeamId(team.getId());
            TeamDto.Response teamDto = team.toDto(stackList, techFieldList,category);

            responseList.add(teamDto);
        }

        CommonDataListResponseDto<Object> listResponseDto = CommonDataListResponseDto.builder()
                .status(200)
                .message("팀 리스트 조회에 성공하였습니다.")
                .dataList(responseList)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(listResponseDto);
    }

    @Operation(summary = "팀 삭제", description = "팀을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀 삭제에 성공하였습니다.")
    })
    @DeleteMapping("/team/{id}")
    public ResponseEntity<CommonResponseDto<Object>> deleteTeam(@PathVariable("id") Long id) {

        teamService.deleteById(id);
        CommonResponseDto<Object> responseDto = CommonResponseDto.builder()
                .id(id)
                .message("팀 삭제에 성공하였습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "팀 수정", description = "팀을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "팀 수정에 성공하였습니다.")
    })
    @PutMapping("/team/{id}")
    public ResponseEntity<CommonResponseDto<Object>> updateTeam(@PathVariable("id") Long teamId, @RequestBody @Valid TeamDto.Update newTeamDto) {

        Team newTeam = newTeamDto.toEntity();

        // 팀
        teamService.update(teamId, newTeam);

        // stack
        List<String> stackNameList = newTeamDto.getRecruitStackNameList();
        stackService.updateByTeam(teamId, stackNameList);

        // techField
        List<String> techFieldNameList = newTeamDto.getRecruitTechFieldNameList();
        techFieldService.updateByTeam(teamId, techFieldNameList);

        //Category
        String categoryName = newTeamDto.getCategoryName();
        categoryService.update(teamId, categoryName);
        CommonResponseDto<Object> responseDto = CommonResponseDto.builder()
                .id(teamId)
                .message("팀 수정에 성공하였습니다.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
