package dev.devpool.repository;

import dev.devpool.domain.Category;
import dev.devpool.domain.Comment;
import dev.devpool.domain.Team;
import dev.devpool.exception.CustomEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;
    private final CommentRepository commentRepository;

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

    public Optional<Team> findOneByName(String name) {
        Optional<Team> findTeam = em.createQuery("select t from Team  t where t.name=:name", Team.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findAny();

        return findTeam;
    }

    public void deleteById(Long teamId) {
        Team findTeam = em.find(Team.class, teamId);
        if (findTeam == null) {
            throw new CustomEntityNotFoundException(Team.class.getName(), teamId);
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

        em.createQuery("delete from Category cg where cg.team.id=:teamId")
                .setParameter("teamId", teamId).executeUpdate();

        em.createQuery("delete from Comment c where c.team.id=:teamId and c.parent is not NULL")
                .setParameter("teamId", teamId).executeUpdate();

        em.createQuery("delete from Comment c where c.team.id=:teamId and c.parent is NULL")
                .setParameter("teamId", teamId).executeUpdate();

        em.createQuery("delete from Team t where t.id=:teamId").
                setParameter("teamId", teamId).executeUpdate();

    }

    public void deleteByHostId(Long hostId) {
        List<Team> teamList = em.createQuery("select t from Team t where t.hostMember.id=:hostId", Team.class)
                .setParameter("hostId", hostId)
                .getResultList();
        if (teamList == null || teamList.size() == 0) {
            throw new CustomEntityNotFoundException(Team.class.getName(), hostId);
        }
        /**
         * 영속성 컨텍스트를 사용하는 queryDsl로 리팩토링 할 예정
         */

        teamList.stream()
                .forEach(s -> deleteById(s.getId()));

    }

    public void deleteAll() {
        List<Team> findTeams = em.createQuery("select t from Team t", Team.class).getResultList();
        for (Team findTeam : findTeams) {
            Long teamId = findTeam.getId();

            deleteById(teamId);
        }

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
