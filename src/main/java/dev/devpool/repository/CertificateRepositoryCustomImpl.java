package dev.devpool.repository;

import dev.devpool.domain.Certificate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CertificateRepositoryCustomImpl implements CertificateRepositoryCustom{
    private final EntityManager em;

    @Override
    public List<Certificate> findAllByMemberId(Long memberId) {
        List<Certificate> certificateList = em.createQuery("select c from Certificate c where c.member.id=:memberId", Certificate.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return certificateList;
    }


    @Override
    public void deleteAllByMemberId(Long memberId) {
        Query query = em.createQuery("delete from Certificate c where c.member.id=:memberId")
                .setParameter("memberId", memberId);
        query.executeUpdate();
    }
}
