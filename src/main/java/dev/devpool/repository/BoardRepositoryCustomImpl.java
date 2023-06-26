package dev.devpool.repository;

import dev.devpool.domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Board> findAllByMemberId(Long memberId) {
        List<Board> boardList = em.createQuery("select b from Board b where b.member.id=:memberId", Board.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return boardList;
    }
}
