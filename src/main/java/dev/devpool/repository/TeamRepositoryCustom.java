package dev.devpool.repository;

import dev.devpool.domain.Team;

import java.util.Optional;

public interface TeamRepositoryCustom {
    Optional<Team> findOneByName(String name);

    Optional<Team> findOneByHostId(Long hostId);

    void deleteByIdCustom(Long teamId);

    void deleteByHostId(Long hostId);

    void deleteAllCustom();
}
