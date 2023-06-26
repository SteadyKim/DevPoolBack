package dev.devpool.repository;

import dev.devpool.domain.Board;
import dev.devpool.domain.BoardComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardCommentRepositoryCustomImpl implements BoardCommentRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<BoardComment> findAllByBoardId(Long boardId) {
        List<BoardComment> boardCommentList = em.createQuery("select bc from BoardComment bc where bc.board.id=:boardId", BoardComment.class)
                .setParameter("boardId", boardId)
                .getResultList();
        return boardCommentList;
    }
}
