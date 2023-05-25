package dev.devpool.repository;

import dev.devpool.domain.MemberTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTeamRepository extends JpaRepository<MemberTeam, Long>, MemberTeamRepositoryCustom {
}
