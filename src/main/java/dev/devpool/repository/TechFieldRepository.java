package dev.devpool.repository;

import dev.devpool.domain.TechField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TechFieldRepository {

    private final EntityManager em;

    @Autowired
    public TechFieldRepository(EntityManager em) {
        this.em = em;
    }


    // 저장
    public Long save(TechField techField) {
        em.persist(techField);

        return techField.getId();
    }

    // 조회
    public TechField findOneById(Long techFieldId) {
        return em.find(TechField.class, techFieldId);
    }

    public List<TechField> findAll() {
        List<TechField> findTechFields = em.createQuery("select tf from TechField tf", TechField.class)
                .getResultList();

        return findTechFields;
    }

    public List<TechField> findAllByMemberId(Long memberId) {
        List<TechField> findTechFields = em.createQuery("select tf from TechField tf where tf.member.id=:memberId", TechField.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return findTechFields;
    }

    public List<TechField> findAllByTeamId(Long teamId) {
        List<TechField> findTechFields = em.createQuery("select tf from TechField tf where tf.team.id=:teamId", TechField.class)
                .setParameter("teamId", teamId)
                .getResultList();

        return findTechFields;
    }

    // 삭제
    public void deleteById(Long techFieldId) {
        TechField findTechField = em.find(TechField.class, techFieldId);
        if (findTechField != null) {
            em.remove(findTechField);
        }
    }

    public void deleteAll() {
        Query query = em.createQuery("delete from TechField tf");
        query.executeUpdate();
    }

    public void deleteAllByMemberId(Long memberId) {
        Query query = em.createQuery("delete from TechField tf where tf.member.id=:memberId")
                .setParameter("memberId", memberId);

        query.executeUpdate();
    }

    public void deleteAllByTeamId(Long teamId) {
        Query query = em.createQuery("delete from TechField tf where tf.team.id=:teamId")
                .setParameter("teamId", teamId);

        query.executeUpdate();
    }
}
