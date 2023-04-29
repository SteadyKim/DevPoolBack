package dev.devpool.service;

import dev.devpool.domain.Member;

import static org.junit.jupiter.api.Assertions.*;

import dev.devpool.exception.GlobalExceptionHandler;
import dev.devpool.exception.member.create.DuplicateMemberException;
import dev.devpool.exception.member.read.MemberNotFoundException;
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
            Member member = Member.builder()
                    .name("김태우")
                    .email("redsas11@ner.com")
                    .password("asdas")
                    .nickName("귀미")
                    .build();
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
        Member member = Member.builder()
                .name("김태우")
                .email("redsas11@ner.com")
                .password("asdas")
                .nickName("귀미")
                .build();
        memberService.join(member);


        //when
        Member member2 = Member.builder()
                .email("redsas11@ner.com")
                .password("tsfd")
                .nickName("귀미")
                .build();

        //then
        assertThrows(DuplicateMemberException.class, () -> {
            memberService.join(member2);
        });
    }

    @Test
    public void 멤버삭제ById () {

            //given
        Member member = Member.builder()
                .name("김태우")
                .email("redsas11@ner.com")
                .password("asdas")
                .nickName("귀미")
                .build();
            memberService.join(member);

            //when
            memberService.deleteById(member.getId());


            //then
            assertThrows(MemberNotFoundException.class, () -> {
                memberService.findOneById(member.getId());
            });
    }

    @Test
    public void 멤버수정() {
        transactionTemplate.execute(status -> {

            //given
            Member member = Member.builder()
                    .name("김태우")
                    .email("redsas11@ner.com")
                    .password("asdas")
                    .nickName("귀미")
                    .build();

            memberService.join(member);

            //when
            // 데이터가 2개만 온 경우 등 issue -> front에서 모두 보내줄 수 있도록 짜달라고 하기...
            String newName = "염진섭";
            String newNickName = "asdasd";
            String Email = "rerewrw2112";
            String password = "asdasd";

            Member newMember = Member.builder()
                    .name(newName)
                    .email(Email)
                    .password(password)
                    .nickName(newNickName)
                    .build();

            em.flush();
            em.clear();
            Member findMember = memberService.update(member.getId(), newMember);

            //then
            assertEquals(findMember.getEmail(), Email);

            return null;
        });
    }



}
