package dev.devpool.repository;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import dev.devpool.exception.CustomEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;
    private final TeamRepository teamRepository;
    /**
     * 향후 @RequiredArgsment로 변경 - lombok
     */

    // 저장
    public void save(Member member) {
            em.persist(member);
    }

    //조회
    public Member findOneById(Long memberId) {
        Member findMember = em.find(Member.class, memberId);

            if (findMember == null) {
            throw new CustomEntityNotFoundException(Member.class.getName(), memberId);
        }

        return findMember;

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
        if (findMember == null) {
            throw new CustomEntityNotFoundException(Member.class.getName(), memberId);
        }
        else {
            List<Project> projectList = em.createQuery("select p from Project p where p.member.id=:memberId", Project.class)
                    .setParameter("memberId", memberId)
                    .getResultList();
            projectList.stream()
                        .forEach(project -> em.createQuery("delete from Stack s where s.project.id=:projectId")
                        .setParameter("projectId", project.getId())
                        .executeUpdate());

            em.remove(findMember);
        }
    }


    public void deleteAll() {
        List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member findMember : findMembers) {
            Long memberId = findMember.getId();
            deleteById(memberId);
        }

    }
}
