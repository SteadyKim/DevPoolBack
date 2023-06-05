package dev.devpool.repository;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import dev.devpool.dto.common.CommonResponseDto;

import java.util.List;

public interface MemberTeamRepositoryCustom {
    void join(Member member, Team team);

    void delete(Member member, Team team);

    List<MemberTeam> findAllByTeamId(Long teamId);
}
