package dev.devpool.repository;

import dev.devpool.domain.TechField;

import java.util.List;

public interface TechFieldRepositoryCustom {

    List<TechField> findAllByMemberId(Long memberId);

    List<TechField> findAllByTeamId(Long teamId);

    void deleteAllByMemberId(Long memberId);

    void deleteAllByTeamId(Long teamId);
}
