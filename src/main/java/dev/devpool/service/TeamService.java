package dev.devpool.service;

import dev.devpool.domain.*;
import dev.devpool.dto.*;
import dev.devpool.exception.CustomDuplicateException;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final StackRepository stackRepository;
    private final TechFieldRepository techFieldRepository;
    private final CategoryRepository categoryRepository;

    private final  StackService stackService;
    private final TechFieldService techFieldService;
    private final CategoryService categoryService;


    @Transactional
    public CommonResponseDto<Object> join(TeamDto.Save teamSaveDto) {
        Team team = teamSaveDto.toEntity();

        validateTeam(team);

        Long memberId = teamSaveDto.getMemberId();

        Member member = memberRepository.findOneById(memberId);


        //MemberTeam Save -> 추후 repo, service 만들어야 할 듯??
        MemberTeam memberTeam = MemberTeam.builder()
                .build();

        // 양방향 연관관계 메서드가 있어서 build에서 넣지 않음
        memberTeam.addMemberTeam(member, team);

        teamRepository.save(team);

        // stack
        teamSaveDto.getRecruitStackNameList()
                .stream()
                .map( dto -> dto.toEntity(team))
                .forEach(stackRepository::save);


        // TechField
        teamSaveDto.getRecruitTechFieldNameList()
                .stream()
                .map(dto -> dto.toEntity(team))
                .forEach(techFieldRepository::save);

        // category
        CategoryDto.Save categoryName = teamSaveDto.getCategoryName();
        Category category = categoryName.toEntity(team);
        categoryRepository.save(category);

        return CommonResponseDto.builder()
                .status(201)
                .message("팀 저장에 성공하였습니다.")
                .id(team.getId())
                .build();
    }

    public TeamDto.Response findOneById(Long teamId) {
        Team findTeam = teamRepository.findOneById(teamId);
        if (findTeam == null) {
            throw new CustomEntityNotFoundException(Team.class.getName(), teamId);
        }

        // stack
        List<Stack> stackList = stackRepository.findAllByTeamId(teamId);

        // techfield
        List<TechField> techFieldList = techFieldRepository.findAllByTeamId(teamId);
        // category
        Category category = categoryRepository.findByTeamId(teamId);

        TeamDto.Response responseDto = findTeam.toDto(stackList, techFieldList, category);


        return responseDto;
    }

    public List<TeamDto.Response> findAll() {
        List<TeamDto.Response> responseDtoList = teamRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Team::getCreateTime))
                .map(team -> findOneById(team.getId()))
                .collect(Collectors.toList());

        return responseDtoList;
    }

    public void validateTeam(Team team) {
        Optional<Team> findTeam = teamRepository.findOneByName(team.getName());

        if (findTeam.isPresent()) {
            throw new CustomDuplicateException(Team.class.getName(), team.getId());
        }
    }
    @Transactional
    public CommonResponseDto<Object> deleteById(Long teamId) {
        teamRepository.deleteById(teamId);

        return CommonResponseDto.builder().id(teamId)
                .message("팀 삭제에 성공하였습니다.").
                build();

    }

    @Transactional
    public CommonResponseDto<Object> deleteAll() {
        teamRepository.deleteAll();

        return CommonResponseDto.builder().
                message("모든 팀 삭제에 성공하였습니다.").build();
    }

    @Transactional
    public CommonResponseDto<Object> update(Long teamId, TeamDto.Update newTeamDto) {
        Team findTeam = teamRepository.findOneById(teamId);

        Team team = newTeamDto.toEntity();

        // 변경감지를 활용해 Update 쿼리
        findTeam.update(team);

        List<StackDto.Save> stackDtoList = newTeamDto.getRecruitStackNameList();
        stackService.updateByTeam(teamId, stackDtoList);

        List<TechFieldDto.Save> techFieldDtoList = newTeamDto.getRecruitTechFieldNameList();
        techFieldService.updateByTeam(teamId, techFieldDtoList);

        CategoryDto.Save categoryDto = newTeamDto.getCategoryName();
        categoryService.update(teamId, categoryDto);

        return CommonResponseDto.builder()
                .id(teamId)
                .message("팀 수정에 성공하였습니다.")
                .build();
    }

    /**
     * MemberTeam
     */
    @Transactional
    public Team updateMemberTeam(Long teamId, Member... members) {
        Team findTeam = teamRepository.findOneById(teamId);
        // 지우고 추가하기...
        teamRepository.deleteAllMemberTeam(findTeam.getId());

        for (Member member : members) {
            MemberTeam memberTeam = new MemberTeam();

            memberTeam.addMemberTeam(member, findTeam);
        }

        return findTeam;
    }

}
