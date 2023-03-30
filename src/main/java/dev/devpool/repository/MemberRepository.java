package dev.devpool.repository;

import dev.devpool.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    public void deleteById(long memberId) {
        Member findMember = em.find(Member.class, memberId);

        em.remove(findMember);
    }

    public void delete(Member member) {

        em.remove(member);
    }

    public void deleteAll() {
        Query deleteAll = em.createQuery("delete from Member m");
        deleteAll.executeUpdate();
    }
}
