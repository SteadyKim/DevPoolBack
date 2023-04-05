package dev.devpool.repository;

import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    public Team findOneById(long teamId) {
        Team findTeam = em.find(Team.class, teamId);

        return findTeam;
    }
    public List<Team> findAll() {
        List<Team> teamList = em.createQuery("select t from Team t", Team.class).getResultList();

        return teamList;
    }

    public void deleteById(long teamId) {
        Team findTeam = em.find(Team.class, teamId);
        em.remove(findTeam);
    }

    public void delete(Team team) {
        em.remove(team);
    }

    public void deleteAll() {
        List<Team> findTeams = em.createQuery("select t from Team t", Team.class).getResultList();
        for (Team findTeam : findTeams) {
            Long teamId = findTeam.getId();
            Query query = em.createQuery("delete from MemberTeam mt where mt.team.id=:teamId")
                    .setParameter("teamId", teamId);
            query.executeUpdate();
        }

        Query query = em.createQuery("delete from Team t");
        query.executeUpdate();
    }

    /**
     * MemberTEAM
     */
    public void deleteAllMemberTeam(Long teamId) {
        Query query = em.createQuery("delete from MemberTeam mt where mt.team.id =:teamId")
                .setParameter("teamId", teamId);

        query.executeUpdate();
    }
}
