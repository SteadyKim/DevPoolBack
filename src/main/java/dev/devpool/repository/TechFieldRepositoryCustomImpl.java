package dev.devpool.repository;

import dev.devpool.domain.TechField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TechFieldRepositoryCustomImpl implements TechFieldRepositoryCustom{
    private final EntityManager em;

    @Override
    public List<TechField> findAllByMemberId(Long memberId) {
        List<TechField> findTechFields = em.createQuery("select tf from TechField tf where tf.member.id=:memberId", TechField.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return findTechFields;
    }

    @Override
    public List<TechField> findAllByTeamId(Long teamId) {
        List<TechField> findTechFields = em.createQuery("select tf from TechField tf where tf.team.id=:teamId", TechField.class)
                .setParameter("teamId", teamId)
                .getResultList();

        return findTechFields;
    }

    @Override
    public void deleteAllByMemberId(Long memberId) {
        Query query = em.createQuery("delete from TechField tf where tf.member.id=:memberId")
                .setParameter("memberId", memberId);

        query.executeUpdate();
    }

    @Override
    public void deleteAllByTeamId(Long teamId) {
        Query query = em.createQuery("delete from TechField tf where tf.team.id=:teamId")
                .setParameter("teamId", teamId);

        query.executeUpdate();
    }
}
