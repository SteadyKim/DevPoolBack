package dev.devpool.repository;


import dev.devpool.domain.Project;
import dev.devpool.domain.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProjectRepository {

    private final EntityManager em;

    @Autowired
    public ProjectRepository(EntityManager em) {
        this.em = em;
    }

    //저장
    public void save(Project project) {
        em.persist(project);
    }


    //조회
    public Project findOneById(Long projectId) {
        return em.find(Project.class, projectId);
    }

    public List<Project> findAll() {
        return em.createQuery("select p from Project p", Project.class)
                .getResultList();
    }

    public List<Project> findAllByMemberId(Long memberId) {
        List<Project> findProjectByMemberId = em.createQuery("select p from Project p where p.member.id=:memberId", Project.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return findProjectByMemberId;
    }

    //삭제
    public void deleteById(Long projectId) {

        Project findProject = em.find(Project.class, projectId);
        em.remove(findProject);
    }

    public void deleteAll() {
        List<Project> projectList = em.createQuery("select p from Project p", Project.class)
                .getResultList();

        for (Project project : projectList) {
            em.remove(project);
        }
    }

    public void deleteByMemberId(Long memberId) {
        List<Project> projectList = findAllByMemberId(memberId);

        for (Project project : projectList) {
            deleteById(project.getId());
        }
    }


    //수정
}
