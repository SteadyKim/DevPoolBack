package dev.devpool.repository;

import dev.devpool.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    //조회
    public Member findOneById(long memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }


}
