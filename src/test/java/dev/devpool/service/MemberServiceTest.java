package dev.devpool.service;

import dev.devpool.domain.Member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

    @AfterEach
    public void 지우기() {
        System.out.println("======@AfterEach======");
        memberService.deleteAll();
    }

    @Test
    public void 멤버저장_조회 () {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();
            member.setName("김dsa우");
            member.setEmail("redasdsas11@ner.com");
            member.setPassword("tasdaasd16");
            member.setNickName("귀미");
            memberService.join(member);

            //when
            em.flush();
            em.clear();

            //then
            Member findMember = memberService.findOneById(member.getId());
            assertEquals(member.getId(), findMember.getId());

            return null;
        });
    }

    @Test
    public void 멤버중복예외() {
        //given
        Member member = new Member();
        member.setName("김우");
        member.setEmail("redsas11@ner.com");
        member.setPassword("taasd16");
        member.setNickName("귀미");
        memberService.join(member);


        //when
        Member member2 = new Member();
        member2.setName("김우");
        member2.setEmail("redsas11@ner.com");
        member2.setPassword("taasd16");
        member2.setNickName("귀미");


        //then
        assertThrows(IllegalArgumentException.class, () -> {
            memberService.join(member2);
        });
    }

    @Test
    public void 멤버삭제ById () {
        transactionTemplate.execute(status -> {

            //given
            Member member = new Member();
            member.setName("김태우");
            member.setEmail("rereeasd11sax@xver.com");
            member.setPassword("taeasd16");
            member.setNickName("귀요미");
            memberService.join(member);

            //when
            memberService.deleteById(member.getId());
            em.flush();
            em.clear();

            //then
            Member findMember = memberService.findOneById(member.getId());
            assertNull(findMember);

            return null;
        });
    }

    @Test
    public void 멤버수정() {
        transactionTemplate.execute(status -> {

            //given
            Member member = new Member();
            member.setName("김태우");
            member.setEmail("re1125@ner.com");
            member.setPassword("taed");
            member.setNickName("귀요미");
            memberService.join(member);

            //when
            // 데이터가 2개만 온 경우 등 issue -> front에서 모두 보내줄 수 있도록 짜달라고 하기...
            String newName = "염진섭";
            String newNickName = "asdasd";
            String Email = "rerewrw2112";
            String password = "asdasd";

            em.flush();
            em.clear();
            Member findMember = memberService.update(member.getId(), newName, newNickName, Email, password);

            //then
            assertEquals(findMember.getEmail(), Email);

            return null;
        });
    }



}
