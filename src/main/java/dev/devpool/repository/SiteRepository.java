package dev.devpool.repository;

import dev.devpool.domain.Latter;
import dev.devpool.domain.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SiteRepository {
    private final EntityManager em;

    @Autowired
    public SiteRepository(EntityManager em) {
        this.em = em;
    }

    // 저장
    public void save(Site site) {
        em.persist(site);
    }

    // 조회
    public List<Site> findAllByMemberId(Long memberId) {
        List<Site> sites = em.createQuery("select s from Site s where s.member.id=:memberId", Site.class)
                .setParameter("memberId", memberId)
                .getResultList();

        return sites;
    }

    public Site findById(Long siteId) {
        Site findSite = em.find(Site.class, siteId);

        return findSite;
    }


    // 삭제
    public void deleteById(Long siteId) {
        Site findSite = em.find(Site.class, siteId);
        em.remove(findSite);
    }

    public void deleteByMemberId(Long memberId) {
        Query query = em.createQuery("delete from Site s where s.member.id=:memberId")
                .setParameter("memberId", memberId);
        query.executeUpdate();
    }

    public void deleteAll() {
        Query query = em.createQuery("delete from Site s");
        query.executeUpdate();
    }
}
