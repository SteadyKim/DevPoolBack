package dev.devpool.repository;

import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class TeamRepository {

    private final EntityManager em;

    @Autowired
    public TeamRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Team team) {
        em.persist(team);
    }

    public Team findOne(long teamId) {
        Team findTeam = em.find(Team.class, teamId);

        return findTeam;
    }

    public List<Team> findAll() {
        List<Team> teamList = em.createQuery("select t from Team t", Team.class).getResultList();

        return teamList;
    }
}
