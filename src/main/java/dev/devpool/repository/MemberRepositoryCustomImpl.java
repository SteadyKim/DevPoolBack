package dev.devpool.repository;

import dev.devpool.domain.Board;
import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import dev.devpool.exception.CustomEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final EntityManager em;

    @Override
    public Optional<Member> findOneByEmail(String email) {
        Optional<Member> findMember = em.createQuery("select m from Member m where m.email=:email", Member.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findAny();

        return findMember;
    }

    @Override
    public void deleteByMemberId(Long memberId) {

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

            List<Board> boardList = em.createQuery("select b from Board b where b.member.id=:memberId", Board.class)
                    .setParameter("memberId", memberId)
                    .getResultList();

            boardList.stream()
                            .forEach(em::remove);

            em.remove(findMember);
        }
    }

    @Override
    public void deleteAllCustom() {
        List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();

        for (Member findMember : findMembers) {
            Long memberId = findMember.getId();
            deleteByMemberId(memberId);
        }
    }
}
