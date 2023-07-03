package dev.devpool.repository;

import dev.devpool.domain.Team;
import dev.devpool.exception.CustomEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TeamRepositoryCustomImpl implements TeamRepositoryCustom {

    private final EntityManager em;

    @Override
    public Optional<Team> findOneByName(String name) {
        return  em.createQuery("select t from Team  t where t.name=:name", Team.class)
                .setParameter("name", name)
                .getResultList()
                .stream()
                .findAny();
    }

    @Override
    public Optional<Team> findOneByHostId(Long hostId) {
        return em.createQuery("select t from Team t where t.hostMember.id=:hostId", Team.class)
                .setParameter("hostId", hostId)
                .getResultStream()
                .findAny();
    }

    @Override
    public void deleteByIdCustom(Long teamId) {
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

    @Override
    public void deleteByHostId(Long hostId) {
        List<Team> teamList = em.createQuery("select t from Team t where t.hostMember.id=:hostId", Team.class)
                .setParameter("hostId", hostId)
                .getResultList();


        teamList.stream()
                .forEach(s -> deleteByIdCustom(s.getId()));

    }

    @Override
    public void deleteAllCustom() {
        List<Team> findTeams = em.createQuery("select t from Team t", Team.class).getResultList();
        for (Team findTeam : findTeams) {
            Long teamId = findTeam.getId();

            deleteByIdCustom(teamId);
        }
    }
}
