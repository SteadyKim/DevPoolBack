package dev.devpool.repository;

import dev.devpool.domain.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Project> findAllByMemberId(Long memberId) {
        return em.createQuery("select p from Project p where p.member.id=:memberId", Project.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void deleteByMemberId(Long memberId) {
        List<Project> projectList = findAllByMemberId(memberId);

        projectList.stream()
                .forEach(em::remove);
    }
}
