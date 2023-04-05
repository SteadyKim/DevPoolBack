package dev.devpool.repository;


import dev.devpool.domain.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CertificateRepository {

    private final EntityManager em;

    @Autowired
    public CertificateRepository(EntityManager em) {
        this.em = em;
    }
    // 저장
    public void save(Certificate certificate) {
        em.persist(certificate);
    }

    // 조회
    public List<Certificate> findAllByMemberId(Long memberId) {
        List<Certificate> certificateList = em.createQuery("select c from Certificate c where c.member.id=:memberId", Certificate.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return certificateList;
    }


    // 삭제 -> Member에 Cascade
    public void deleteById(Long certificateId) {
        Certificate certificate = em.find(Certificate.class, certificateId);
        em.remove(certificate);
    }

    public void deleteAll() {
        Query query = em.createQuery("delete from Certificate c");
        query.executeUpdate();
    }
}
