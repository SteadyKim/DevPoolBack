package dev.devpool.repository;

import dev.devpool.domain.Board;

import java.util.List;

public interface BoardRepositoryCustom {
    List<Board> findAllByMemberId(Long memberId);
}
