package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.exception.CustomException;
import dev.devpool.repository.MemberRepository;
import dev.devpool.repository.MemberTeamRepository;
import dev.devpool.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberTeamService {

    private final MemberRepository memberRepository;

    private final TeamRepository teamRepository;

    private final MemberTeamRepository memberTeamRepository;
    private final EntityManager em;

    // 팀에 멤버 조인
    @Transactional
    public CommonResponseDto<Object> join(Long memberId, Long teamId) {
        Member findMember = memberRepository.findOneById(memberId);

        Team findTeam = teamRepository.findOneById(teamId);

        Integer recruitCount = findTeam.getRecruitCount();

        int currentCount = findTeam.getMemberTeams().size();

        // 가득차있습니다.
        if(currentCount >= recruitCount) {
            throw new CustomException("현재 팀에 인원이 가득차 있습니다.", MemberTeamService.class.getName(), "join()");
        }

        // 이미 있는 회원입니다.
        List<MemberTeam> memberTeamList = findTeam.getMemberTeams();

        boolean isDuplicateMember = memberTeamList.stream()
                .map(memberTeam -> memberTeam.getMember().getId())
                .anyMatch(memberTeamMemberId -> Objects.equals(memberTeamMemberId, findMember.getId()));

        if (isDuplicateMember) {
            throw new CustomException("현재 팀에 중복된 멤버입니다.", MemberTeamService.class.getName(), "join()");
        }

        memberTeamRepository.join(findMember, findTeam);

        return CommonResponseDto.builder()
                .message("팀에 멤버를 저장하였습니다.")
                .status(201)
                .build();
    }

}
