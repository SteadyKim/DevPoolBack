package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProjectServiceTest {
    @Autowired
    ProjectService projectService;

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

    @AfterEach
    public void 지우기() {
        System.out.println("======@AfterEach======");
        projectService.deleteAll();
    }

    @Test
    public void 프로젝트저장_조희() {
        transactionTemplate.execute(status -> {
            //given
            Project project = Project.builder()
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .name("JPA")
                    .build();

            projectService.join(project);

            //when
            em.flush();
            em.clear();

            //then
            Project findProject = projectService.findOneById(project.getId());
            assertEquals(findProject.getId(), project.getId());
            return null;
        });
    }

    @Test
    public void 멤버아이디로_프로젝트저장조회() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();

            Project project = Project.builder()
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .name("JPA")
                    .member(member)
                    .build();

            Project project2 = Project.builder()
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .name("JPA")
                    .member(member)
                    .build();


            memberService.join(member);

            projectService.join(project);
            projectService.join(project2);


            //when
            em.flush();
            em.clear();

            List<Project> projectsByMemberId = projectService.findProjectsByMemberId(member.getId());


            //then
            assertEquals(2, projectsByMemberId.size());

            return null;
        });
    }

    @Test
    public void 프로젝트삭제ById() {
        transactionTemplate.execute(status -> {
            //given
            Project project = new Project();
            projectService.join(project);

            //when
            em.flush();
            em.clear();

            projectService.deleteById(project.getId());

            //then
            em.flush();
            em.clear();

            Project findProject = projectService.findOneById(project.getId());

            assertNull(findProject);
            return null;
        });
    }

    @Test
    public void 프로젝트삭제ByMemberId() {
        transactionTemplate.execute(status -> {
            //given
            Member member1 = new Member();

            Project project = Project.builder()
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .name("JPA")
                    .member(member1)
                    .build();

            Project project2 = Project.builder()
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .name("JPA")
                    .member(member1)
                    .build();

            memberService.join(member1);
            projectService.join(project);
            projectService.join(project2);

            //when
            em.flush();
            em.clear();

            projectService.deleteByMemberId(member1.getId());

            //then
            em.flush();
            em.clear();

            List<Project> projectsByMemberId = projectService.findProjectsByMemberId(member1.getId());

            assertEquals(0, projectsByMemberId.size());
            return null;
        });
    }
}