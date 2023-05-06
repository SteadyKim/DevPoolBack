package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Site;
import dev.devpool.repository.SiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SiteService {
    private final SiteRepository siteRepository;


    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }


    // 저장
    @Transactional
    public Long join(Site site) {
        siteRepository.save(site);

        return site.getId();
    }

    // 조회
    public List<Site> findAllByMemberId(Long memberId) {
        List<Site> sites = siteRepository.findAllByMemberId(memberId);

        return sites;
    }

    public Site findById(Long siteId) {
        Site findSite = siteRepository.findById(siteId);
        return findSite;
    }

    // 삭제
    @Transactional
    public void deleteById(Long siteId) {
        siteRepository.deleteById(siteId);
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        siteRepository.deleteAllByMemberId(memberId);
    }
    @Transactional
    public void deleteAll() {
        siteRepository.deleteAll();
    }

    // 수정
    @Transactional
    public void update(Member member, ArrayList<Site> sites) {
        // 지우고
        siteRepository.deleteAllByMemberId(member.getId());
        // 추가
        for (Site site : sites) {
            member.addSite(site);
            siteRepository.save(site);
        }
    }
}
