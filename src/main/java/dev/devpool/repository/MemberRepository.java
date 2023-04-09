package dev.devpool.repository;

import dev.devpool.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

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
    public Member findOneById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public Optional<Member> findOneByEmail(String email) {
        Optional<Member> findMember = em.createQuery("select m from Member m where m.email=:email", Member.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findAny();

        return findMember;
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public void deleteById(Long memberId) {
        Member findMember = em.find(Member.class, memberId);
        if (findMember != null) {
            em.remove(findMember);
        }
    }


    public void deleteAll() {
        List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member findMember : findMembers) {
            Long memberId = findMember.getId();
            Query query = em.createQuery("delete from Certificate c where c.member.id=:memberId")
                    .setParameter("memberId", memberId);
            query.executeUpdate();
        }

        for (Member findMember : findMembers) {
            Long memberId = findMember.getId();
            Query query = em.createQuery("delete from Latter l where l.member.id=:memberId")
                    .setParameter("memberId", memberId);
            query.executeUpdate();
        }

        Query deleteAll = em.createQuery("delete from Member m");
        deleteAll.executeUpdate();
    }
}
