package dev.devpool.repository;


import dev.devpool.domain.Stack;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class StackRepository {

    private final EntityManager em;


    public StackRepository(EntityManager em) {
        this.em = em;
    }

    //저장
    public void save(Stack stack) {
        em.persist(stack);
    }

    //조회
    public Stack findOneById(Long stackId) {
        return em.find(Stack.class, stackId);
    }

    public List<Stack> findAllByTeamId(Long teamId) {
        List<Stack> stackList = em.createQuery("select s from Stack s where s.team.id=:teamId", Stack.class)
                .setParameter("teamId", teamId)
                .getResultList();
        return stackList;
    }

    public List<Stack> findAllByMemberId(Long memberId) {
        List<Stack> stackList = em.createQuery("select s from Stack s where s.member.id=:memberId", Stack.class)
                .setParameter("memberId", memberId)
                .getResultList();
        return stackList;
    }

    public List<Stack> findAllByProjectId(Long projectId) {
        List<Stack> stackList = em.createQuery("select s from Stack s where s.project.id=:projectId", Stack.class)
                .setParameter("projectId", projectId)
                .getResultList();
        return stackList;
    }

    // 삭제
    public void delete(Stack stack) {
        em.remove(stack);
    }

    public void deleteById(Long stackId) {
        Stack findStack = em.find(Stack.class, stackId);
        em.remove(findStack);
    }


    public void deleteAll() {
        Query deleteAll = em.createQuery("delete from Stack s");
        deleteAll.executeUpdate();
    }

    public void deleteByTeamId(Long teamId) {
        Query query = em.createQuery("delete from Stack s where s.team.id =:teamId")
                .setParameter("teamId", teamId);

        query.executeUpdate();
    }

    public void deleteByMemberId(Long memberId) {
        Query query = em.createQuery("delete from Stack s where s.member.id =:memberId")
                .setParameter("memberId", memberId);

        query.executeUpdate();
    }

    public void deleteByProjectId(Long projectId) {
        Query query = em.createQuery("delete from Stack s where s.project.id =:projectId")
                .setParameter("projectId", projectId);

        query.executeUpdate();
    }


    //수정
}
