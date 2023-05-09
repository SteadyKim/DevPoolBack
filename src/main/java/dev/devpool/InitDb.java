package dev.devpool;

import dev.devpool.domain.*;
import dev.devpool.service.MemberService;
import dev.devpool.service.StackService;
import dev.devpool.service.TeamService;
import dev.devpool.service.TechFieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private final MemberService memberService;

    private final StackService stackService;

    private final TechFieldService techFieldService;
    private final TeamService teamService;

    @PostConstruct
    public void init() {
        initService.ss();

        Team team = Team.builder()
                .name("aa팀을 모집합니디.")
                .totalNum(4)
                .content("ㅗ디ㅣㅐ 째깅!")
                .build();

        Member findMember = memberService.findOneById(1L);

        MemberTeam memberTeam = new MemberTeam();
        memberTeam.addMemberTeam(findMember, team);

        teamService.join(team);

        Stack stack = Stack.builder()
                .name("AWS")
                .team(team)
                .build();
        stackService.join(stack);

        TechField techField = TechField.builder()
                .name("AI")
                .team(team)
                .build();

        techFieldService.join(techField);

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;


        public void ss() {
            Member member = Member.builder()
                    .name("김태우")
                    .nickName("찐빵")
                    .email("ㄱㄷㄱㄷ@naver.com")
                    .password("rter141")
                    .createTime(LocalDateTime.of(2022,5,15,4,3))
                    .imageUrl("asdojasdsa/.sdfsdjfnj")
                    .build();


            em.persist(member);
            Stack stack1 = Stack.builder()
                    .member(member)
                    .name("Stack1")
                    .build();

            Stack stack2 = Stack.builder()
                    .member(member)
                    .name("Stack2")
                    .build();

            Stack stack3 = Stack.builder()
                    .member(member)
                    .name("Stack3")
                    .build();
            em.persist(stack1);
            em.persist(stack2);
            em.persist(stack3);

            TechField techField1 = TechField.builder()
                    .name("AI")
                    .member(member)
                    .build();

            TechField techField2 = TechField.builder()
                    .name("Back_end")
                    .member(member)
                    .build();

            TechField techField3 = TechField.builder()
                    .name("FrontEnd")
                    .member(member)
                    .build();

            em.persist(techField1);
            em.persist(techField2);
            em.persist(techField3);


            Certificate certificate1 = Certificate.builder()
                    .member(member)
                    .name("정보처리기사")
                    .build();

            em.persist(certificate1);

            Member member2 = Member.builder()
                    .name("이영진")
                    .nickName("ㄴ빵")
                    .createTime(LocalDateTime.now())
                    .email("ㄱㄷㄴㄴㄷ@naver.com")
                    .password("rter141")
                    .imageUrl("asdㅌㅌㄴjasdsa/.sdfsdjfnj")
                    .build();

            em.persist(member2);

            Stack stack4 = Stack.builder()
                    .member(member2)
                    .name("Stack4")
                    .build();

            Stack stack5 = Stack.builder()
                    .member(member2)
                    .name("Stack5")
                    .build();

            Stack stack6 = Stack.builder()
                    .member(member2)
                    .name("Stack6")
                    .build();
            em.persist(stack4);
            em.persist(stack5);
            em.persist(stack6);

            TechField techField4 = TechField.builder()
                    .name("AI")
                    .member(member2)
                    .build();

            TechField techField5 = TechField.builder()
                    .name("BackEnd")
                    .member(member2)
                    .build();

            TechField techField6 = TechField.builder()
                    .name("Cloud")
                    .member(member2)
                    .build();

            em.persist(techField4);
            em.persist(techField5);
            em.persist(techField6);


            Certificate certificate2 = Certificate.builder()
                    .member(member2)
                    .name("정보처리기사2")
                    .build();

            em.persist(certificate2);

            Member member3 = Member.builder()
                    .name("염진섭")
                    .nickName("ㄴ빵")
                    .email("ㄱㄷㅇㄴㅇㄴㄷ@naver.com")
                    .password("rterㅇ41")
                    .imageUrl("asdㅌㅌㄴjasdsa/.sdfsdjfnj")
                    .build();

            em.persist(member3);




        }
    }

}
