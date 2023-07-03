package dev.devpool.repository;

import dev.devpool.domain.Stack;
import dev.devpool.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryCustom {
}
