package dev.devpool.repository;

import dev.devpool.domain.Project;

import java.util.List;

public interface ProjectRepositoryCustom {

    List<Project> findAllByMemberId(Long memberId);

    void deleteByMemberId(Long memberId);
}
