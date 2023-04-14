package dev.devpool.service;

import dev.devpool.domain.Certificate;
import dev.devpool.domain.Latter;
import dev.devpool.domain.Member;
import dev.devpool.domain.enums.IsCheck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static dev.devpool.domain.enums.IsCheck.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LatterServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    LatterService latterService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

//    @AfterEach
//    public void 지우기() {
//        System.out.println("======@AfterEach======");
//        memberService.deleteAll();
//    }

    @Test
    public void 쪽지저장_조회ByMember() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();

            Latter latter1 = new Latter();

            latter1.setIsCheck(TRUE);
            Latter latter2 = new Latter();
            member.addLatter(latter1);
            member.addLatter(latter2);
//
            memberService.join(member);
            latterService.join(latter1);
            latterService.join(latter2);
//
            //when
            em.flush();
            em.clear();

            ArrayList<Long> ids = new ArrayList<>();
            ids.add(latter1.getId());
            ids.add(latter2.getId());

            //then
            Member findMember = memberService.findOneById(member.getId());
            List<Latter> findLatters = latterService.findAllByMemberId(findMember.getId());

            //개수
            assertEquals(2, findLatters.size());

            for (Latter latter : findLatters) {
                assertTrue(ids.contains(latter.getId()));
            }

            return null;
        });
    }

    @Test
    public void 쪽지삭제ByMember() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();

            Latter latter1 = new Latter();
            Latter latter2 = new Latter();

            member.addLatter(latter1);
            member.addLatter(latter2);

            memberService.join(member);
            latterService.join(latter1);
            latterService.join(latter2);

            em.flush();
            em.clear();

            //when
            memberService.deleteById(member.getId());
            em.flush();
            em.clear();

            //then
            List<Latter> latters = latterService.findAllByMemberId(member.getId());
            assertEquals(0, latters.size());

            return null;
        });
    }

    @Test
    public void 쪽지삭제ById() {
        transactionTemplate.execute(status -> {
            // given
            Latter latter = new Latter();
            latterService.join(latter);

            // when
            em.flush();
            em.clear();
            latterService.deleteById(latter.getId());
            em.flush();
            em.clear();

            // then
            Latter findLatter = latterService.findById(latter.getId());
            assertNull(findLatter);
            return null;
        });
    }

}