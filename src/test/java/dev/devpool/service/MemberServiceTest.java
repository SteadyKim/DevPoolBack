package dev.devpool.service;

import dev.devpool.domain.Member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Test
    public void 멤버저장_조회 () {
        //given
        Member member = new Member();
        member.setName("김태우");
        member.setEmail("rereers1125@naver.com");
        member.setPassword("taeu4616");
        member.setNickName("귀요미");

        //when
        memberService.join(member);
        em.flush();
        em.clear();

        //then
        Member findMember = memberService.findOne(member.getId());
        assertEquals(member.getId(), findMember.getId());
    }

}
