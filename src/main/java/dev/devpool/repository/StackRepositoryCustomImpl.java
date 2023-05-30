package dev.devpool.repository;

import dev.devpool.domain.Stack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StackRepositoryCustomImpl implements StackRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Stack> findAllByTeamId(Long teamId) {
        return em.createQuery("select s from Stack s where s.team.id=:teamId", Stack.class)
                .setParameter("teamId", teamId)
                .getResultList();
    }

    @Override
    public List<Stack> findAllByMemberId(Long memberId) {
        return em.createQuery("select s from Stack s where s.member.id=:memberId", Stack.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public List<Stack> findAllByProjectId(Long projectId) {
        return em.createQuery("select s from Stack s where s.project.id=:projectId", Stack.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    @Override
    public void deleteAllByTeamId(Long teamId) {
        em.createQuery("delete from Stack s where s.team.id =:teamId")
                .setParameter("teamId", teamId).executeUpdate();
    }

    @Override
    public void deleteAllByMemberId(Long memberId) {
        em.createQuery("delete from Stack s where s.member.id =:memberId")
                .setParameter("memberId", memberId).executeUpdate();
    }

    @Override
    public void deleteAllByProjectId(Long projectId) {
        em.createQuery("delete from Stack s where s.project.id =:projectId")
                .setParameter("projectId", projectId).executeUpdate();
    }
}
