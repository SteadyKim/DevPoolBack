package dev.devpool.repository;

import dev.devpool.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MemberRepository {

    private final EntityManager em;

    /**
     * 향후 @RequiredArgsment로 변경 - lombok
     */
    @Autowired
    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    // 저장
    public void save(Member member) {
        em.persist(member);
    }


}
