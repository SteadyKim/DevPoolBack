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

    public Team findOne(long teamId) {
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
        // 부모 1 자식1 x 10 인 경우 delete 쿼리가 총 20개 나간다
        // 그럼 직접 JPQL로 delete from memberTeam, delete from Team으로 해서 20 -> 2개로 줄일 수 있지 않을까??
        List<Team> teamList = em.createQuery("select t from Team  t", Team.class).getResultList();
        for (Team team : teamList) {
            em.remove(team);
        }
    }
}
