package dev.devpool.service;

import dev.devpool.domain.*;
import dev.devpool.dto.*;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.exception.CustomDuplicateException;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
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
        Long hostMemberId = teamSaveDto.getHostMemberId();
        Member hostMember = memberRepository.findOneById(hostMemberId);

        Team team = Team.builder()
                .hostMember(hostMember)
                .content(teamSaveDto.getContent())
                .recruitCount(teamSaveDto.getRecruitCount())
                .name(teamSaveDto.getName())
                .build();

        validateTeam(team);

        teamRepository.save(team);

        // stack
        teamSaveDto.getRecruitStack()
                .stream()
                .map( dto -> Stack.builder()
                        .name(dto.getName())
                        .team(team).build())
                .forEach(stackRepository::save);


        // TechField
        teamSaveDto.getRecruitTechField()
                .stream()
                .map(dto -> TechField.builder()
                        .name(dto.getName())
                        .team(team).build())
                .forEach(techFieldRepository::save);

        // category
        CategoryDto.Save categoryDto = teamSaveDto.getCategory();
        Category category = Category.builder()
                .team(team)
                .name(categoryDto.getName())
                .build();

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
        List<StackDto.Response> stackDtoList = stackRepository.findAllByTeamId(teamId)
                .stream()
                .map(stack -> StackDto.Response.builder()
                        .name(stack.getName())
                        .build())
                .collect(Collectors.toList());

        // techfield
        List<TechField> techFieldList = techFieldRepository.findAllByTeamId(teamId);

        List<TechFieldDto.Response> techFieldDtoList = techFieldList.stream()
                .map(techField -> TechFieldDto.Response.builder()
                        .name(techField.getName())
                        .build())
                .collect(Collectors.toList());

        // category
        Category category = categoryRepository.findByTeamId(teamId);
        CategoryDto.Response categoryDto = CategoryDto.Response
                .builder()
                .name(category.getName())
                .build();

        // HostMember
        Member hostMember = findTeam.getHostMember();
        if(hostMember == null){
            throw new CustomEntityNotFoundException(Member.class.getName(), teamId);
        }

        TeamDto.Response responseDto = TeamDto.Response.builder()
                .teamId(teamId)
                .name(findTeam.getName())
                .content(findTeam.getContent())
                .category(categoryDto)
                .currentCount(findTeam.getMemberTeams().size())
                .recruitCount(findTeam.getRecruitCount())
                .createTime(findTeam.getCreateTime())
                .recruitStack(stackDtoList)
                .recruitTechField(techFieldDtoList)
                .hostMember(MemberDto.Response.builder()
                        .memberId(hostMember.getId())
                        .name(hostMember.getName())
                        .email(hostMember.getNickName())
                        .imageUrl(hostMember.getImageUrl())
                        .email(hostMember.getEmail())
                        .build())
                .build();

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
    public CommonResponseDto<Object> deleteByHostId(Long hostId) {
        teamRepository.deleteByHostId(hostId);

        return CommonResponseDto.builder().id(hostId)
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

        // 변경감지를 활용해 Update 쿼리
        findTeam.update(newTeamDto);

        List<StackDto.Save> stackDtoList = newTeamDto.getRecruitStack();
        stackService.updateByTeam(teamId, stackDtoList);

        List<TechFieldDto.Save> techFieldDtoList = newTeamDto.getRecruitTechField();
        techFieldService.updateByTeam(teamId, techFieldDtoList);

        CategoryDto.Save categoryDto = newTeamDto.getCategory();
        categoryService.update(teamId, categoryDto);

        return CommonResponseDto.builder()
                .id(teamId)
                .message("팀 수정에 성공하였습니다.")
                .build();
    }

    /**
     * MemberTeam
     */
//    @Transactional
//    public Team updateMemberTeam(Long teamId, List<MemberDto.Save> memberDtoList ) {
//        Team findTeam = teamRepository.findOneById(teamId);
//        // 지우고 추가하기...
//        teamRepository.deleteAllMemberTeam(findTeam.getId());
//
//        for (MemberDto.Save memberDto : memberDtoList) {
//            memberDt
//        }
//
//
//        for (Member member : members) {
//            MemberTeam memberTeam = MemberTeam.builder()
//                    .team(findTeam)
//                    .member(member)
//                    .build();
//
//        }
//
//        return findTeam;
//    }

}
