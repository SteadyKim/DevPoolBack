package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Site;
import dev.devpool.dto.SiteDto;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.repository.MemberRepository;
import dev.devpool.repository.SiteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SiteService {
    private final SiteRepository siteRepository;

    private final MemberRepository memberRepository;


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
    public void update(Long memberId, ArrayList<SiteDto.Save> siteDtoList) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomEntityNotFoundException(Member.class.getName(), memberId));

        // 지우고
        siteRepository.deleteAllByMemberId(findMember.getId());
        // 추가
        for (SiteDto.Save siteDto : siteDtoList) {
            Site site = Site.builder()
                    .member(findMember)
                    .url(siteDto.getUrl())
                    .build();

            siteRepository.save(site);
        }
    }
}
