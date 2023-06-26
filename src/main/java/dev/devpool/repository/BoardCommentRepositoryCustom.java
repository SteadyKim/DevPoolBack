package dev.devpool.repository;

import dev.devpool.domain.BoardComment;

import java.util.List;

public interface BoardCommentRepositoryCustom {
    List<BoardComment> findAllByBoardId(Long boardId);
}
