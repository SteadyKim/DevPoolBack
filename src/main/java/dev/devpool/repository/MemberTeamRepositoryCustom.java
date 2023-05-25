package dev.devpool.repository;

import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import dev.devpool.dto.common.CommonResponseDto;

public interface MemberTeamRepositoryCustom {
    void join(Member member, Team team);
}
