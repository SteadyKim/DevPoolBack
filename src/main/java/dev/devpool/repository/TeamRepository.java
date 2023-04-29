package dev.devpool.repository;

import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import dev.devpool.exception.team.delete.DeleteTeamNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

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

    public Optional<Team> findOneByTitle(String title) {
        Optional<Team> findTeam = em.createQuery("select t from Team  t where t.title=:title", Team.class)
                .setParameter("title", title)
                .getResultList()
                .stream()
                .findAny();

        return findTeam;
    }

    public void deleteById(long teamId) {
        Team findTeam = em.find(Team.class, teamId);
        if (findTeam == null) {
            throw new DeleteTeamNotFoundException();
        }
        /**
         * 영속성 컨텍스트를 사용하는 queryDsl로 리팩토링 할 예정
         */
        em.createQuery("delete from MemberTeam mt where mt.team.id=:teamId")
                .setParameter("teamId", teamId).executeUpdate();

        em.createQuery("delete from Stack s where s.team.id=:teamId")
                .setParameter("teamId", teamId).executeUpdate();

        em.createQuery("delete from TechField tf where tf.team.id=:teamId")
                .setParameter("teamId", teamId).executeUpdate();
        em.createQuery("delete from Comment c where c.team.id=:teamId")
                .setParameter("teamId", teamId).executeUpdate();

        em.createQuery("delete from Team t where t.id=:teamId").
                setParameter("teamId", teamId).executeUpdate();

    }

    public void deleteAll() {
        List<Team> findTeams = em.createQuery("select t from Team t", Team.class).getResultList();
        for (Team findTeam : findTeams) {
            Long teamId = findTeam.getId();
            em.createQuery("delete from Stack s where s.team.id=:teamId")
                    .setParameter("teamId", teamId).executeUpdate();

            em.createQuery("delete from TechField tf where tf.team.id=:teamId")
                    .setParameter("teamId", teamId).executeUpdate();

            em.createQuery("delete from Comment c where c.team.id=:teamId")
                    .setParameter("teamId", teamId).executeUpdate();
        }
        em.createQuery("delete from MemberTeam mt").executeUpdate();
        em.createQuery("delete from Team t").executeUpdate();
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
