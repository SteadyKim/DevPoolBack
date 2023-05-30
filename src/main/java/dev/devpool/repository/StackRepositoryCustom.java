package dev.devpool.repository;

import dev.devpool.domain.Stack;

import java.util.List;

public interface StackRepositoryCustom {

    List<Stack> findAllByTeamId(Long teamId);

    List<Stack> findAllByMemberId(Long memberId);

    List<Stack> findAllByProjectId(Long projectId);

    void deleteAllByTeamId(Long teamId);

    void deleteAllByMemberId(Long memberId);

    void deleteAllByProjectId(Long projectId);
}
