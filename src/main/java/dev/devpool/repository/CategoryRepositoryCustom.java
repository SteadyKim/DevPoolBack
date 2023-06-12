package dev.devpool.repository;

import dev.devpool.domain.Category;

public interface CategoryRepositoryCustom {
    Category findByTeamId(Long teamId);

    void deleteByTeamId(Long teamId);
}
