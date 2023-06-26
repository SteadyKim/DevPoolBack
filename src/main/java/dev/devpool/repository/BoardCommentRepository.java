package dev.devpool.repository;

import dev.devpool.domain.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long>, BoardCommentRepositoryCustom {
}
