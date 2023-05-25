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



        memberTeamRepository.join(findMember, findTeam);

        return CommonResponseDto.builder()
                .message("팀에 멤버를 저장하였습니다.")
                .status(201)
                .build();
    }

}
