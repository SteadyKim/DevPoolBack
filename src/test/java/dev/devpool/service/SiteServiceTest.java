package dev.devpool.service;

import dev.devpool.domain.Certificate;
import dev.devpool.domain.Member;
import dev.devpool.domain.Site;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SiteServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    SiteService siteService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

    @AfterEach
    public void 지우기() {
        System.out.println("======@AfterEach======");
        siteService.deleteAll();
        memberService.deleteAll();
    }

    @Test
    public void 사이트저장_조회() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();

            Site site1 = new Site();
            Site site2 = new Site();

            member.addSite(site1);
            member.addSite(site2);


            memberService.join(member);

            siteService.join(site1);
            siteService.join(site2);


            //when
            em.flush();
            em.clear();

            ArrayList<Long> ids = new ArrayList<>();
            ids.add(site1.getId());
            ids.add(site2.getId());

            //then
            Member findMember = memberService.findOneById(member.getId());
            List<Site> findSites = siteService.findAllByMemberId(findMember.getId());

            // 개수
            assertEquals(2, findSites.size());

            for (Site findSite : findSites) {
                assertTrue(ids.contains(findSite.getId()));
            }

            return null;
        });
    }

    @Test
    public void 자격증삭제ByMember() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();

            Site site1 = new Site();
            Site site2 = new Site();

            member.addSite(site1);
            member.addSite(site2);


            memberService.join(member);

            siteService.join(site1);
            siteService.join(site2);


            //when
            em.flush();
            em.clear();

            Member findMember = memberService.findOneById(member.getId());
            siteService.deleteByMemberId(findMember.getId());

            em.flush();
            em.clear();

            //then
            List<Site> findSites = siteService.findAllByMemberId(member.getId());
            assertEquals(0, findSites.size());

            return null;
        });
    }

    @Test
    public void 사이트삭제ById() {
        transactionTemplate.execute(status -> {

            //given
            Site site1 = new Site();
            siteService.join(site1);

            em.flush();
            em.clear();

            //when
            siteService.deleteById(site1.getId());
            em.flush();
            em.clear();

            //then
            Site findSite = siteService.findById(site1.getId());

            assertNull(findSite);

            return null;
        });
    }

    @Test
    public void 사이트수정() {
        transactionTemplate.execute(status -> {
            // given
            Member member = new Member();
            Site site1 = new Site();
            Site site2 = new Site();
            Site site3 = new Site();
            Site site4 = new Site();


            member.addSite(site1);
            member.addSite(site2);

            memberService.join(member);
            siteService.join(site1);
            siteService.join(site2);

            // when
            ArrayList<Long> ids = new ArrayList<>();

            em.flush();
            em.clear();

            ArrayList<Site> sites = new ArrayList<>();
            sites.add(site3);
            sites.add(site4);

            Member findMember = memberService.findOneById(member.getId());
            siteService.update(findMember, sites);

            em.flush();
            em.clear();

            ids.add(site3.getId());
            ids.add(site4.getId());

            // then
            Member findedMember = memberService.findOneById(member.getId());
            List<Site> findsites = siteService.findAllByMemberId(findedMember.getId());
            for (Site site : findsites) {
                assertTrue(ids.contains(site.getId()));
            }

            return null;
        });
    }
}