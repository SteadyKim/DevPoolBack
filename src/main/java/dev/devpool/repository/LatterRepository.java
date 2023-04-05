package dev.devpool.repository;


import dev.devpool.domain.Latter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class LatterRepository {
    private final EntityManager em;

    @Autowired
    public LatterRepository(EntityManager em) {
        this.em = em;
    }

    // 저장
    public void save(Latter letter) {
        em.persist(letter);
    }

    // 조회
    public List<Latter> findAllByMemberId(Long memberId) {
        List<Latter> latters = em.createQuery("select l from Latter l where l.member.id=:memberId", Latter.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return latters;
    }

    public Latter findById(Long latterId) {
        Latter findLatter = em.find(Latter.class, latterId);

        return findLatter;
    }


    // 삭제
    public void deleteById(Long latterId) {
        Latter latter = em.find(Latter.class, latterId);
        em.remove(latter);
    }

    public void deleteByMemberId(Long memberId) {
        Query query = em.createQuery("delete from Latter l where l.member.id=:memberId")
                .setParameter("memberId", memberId);
        query.executeUpdate();
    }

    public void deleteAll() {
        Query query = em.createQuery("delete from Latter c");
        query.executeUpdate();
    }
}
