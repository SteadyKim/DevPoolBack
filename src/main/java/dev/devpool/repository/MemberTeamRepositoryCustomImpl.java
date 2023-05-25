package dev.devpool.repository;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import dev.devpool.dto.common.CommonResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberTeamRepositoryCustomImpl implements MemberTeamRepositoryCustom{

    private final EntityManager em;

    @Override
    public void join(Member member, Team team) {
        MemberTeam memberTeam = MemberTeam.builder()
                .member(member)
                .team(team)
                .build();

        em.persist(memberTeam);
    }
}
