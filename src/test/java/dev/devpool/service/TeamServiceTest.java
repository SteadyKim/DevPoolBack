package dev.devpool.service;

import dev.devpool.domain.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class TeamServiceTest {

    @Autowired
    TeamService teamService;

    @Autowired
    EntityManager em;

    @Test
    void 팀_저장_조회() {
        Team team = new Team();
        team.setBody("asdasdas");
        team.setTitle("A팀");
        team.setName("fsdf");
        team.setTotal_num(4);
        team.setRecruited_num(0);

        teamService.join(team);

        em.flush();
        em.clear();

        Team findTeam = teamService.findOne(team.getId());

        assertEquals(findTeam.getId(), team.getId());

    }

}