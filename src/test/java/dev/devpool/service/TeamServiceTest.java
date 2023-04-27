package dev.devpool.service;

import dev.devpool.domain.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

    @AfterEach
    public void deleteAll() {
        System.out.println("=====@AfterEach=====");
        teamService.deleteAll();
    }

    @Test
    public void 팀_저장_조회() {
        transactionTemplate.execute(status -> {
            Team team = new Team();
            team.setBody("asdasdas");
            team.setTitle("A팀");
            team.setTotalNum(4);

            teamService.join(team);

            em.flush();
            em.clear();

            Team findTeam = teamService.findOneById(team.getId());

            assertEquals(findTeam.getId(), team.getId());
            return null;

        });


    }

    @Test
    public void 팀삭제ById () {
        transactionTemplate.execute(status -> {
            //given
            Team team = new Team();
            team.setBody("asdasdas");
            team.setTitle("A팀");
            team.setTotalNum(4);

            teamService.join(team);

            //when
            teamService.deleteById(team.getId());
            em.flush();
            em.clear();

            //then
            Team findTeam = teamService.findOneById(team.getId());
            assertNull(findTeam);

            return null;
        });

    }

    @Test
    public void 팀삭제 () {

        transactionTemplate.execute(status -> {
            //given
            Team team = new Team();
            team.setBody("asdasdas");
            team.setTitle("A팀");
            team.setTotalNum(4);

            teamService.join(team);

            //when
            teamService.delete(team);
            em.flush();
            em.clear();

            //then
            Team findTeam = teamService.findOneById(team.getId());
            assertNull(findTeam);
            return null;
        });
    }

    @Test
    public void 팀수정() {
        transactionTemplate.execute(status -> {
            //given
            Team team = new Team();
            team.setBody("asdasdas");
            team.setTitle("A팀");
            team.setTotalNum(4);

            teamService.join(team);

            //when
            String newTeamName = "sadasd";
            String newTitle = "sadad";
            String newBody = "asdasd";
            int newTotalNum = 4;

            Team newTeam = new Team();
            newTeam.setTitle(newTitle);
            newTeam.setBody(newBody);
            newTeam.setTotalNum(newTotalNum);

            em.flush();
            em.clear();

            //then
            Team findTeam = teamService.update(team.getId(), newTeam);

            assertEquals(findTeam.getTitle(), newTitle);
            return null;
        });
    }
}