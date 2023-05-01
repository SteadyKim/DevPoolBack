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
                .title("aa팀을 모집합니디.")
                .totalNum(4)
                .body("ㅗ디ㅣㅐ 째깅!")
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
                    .imageUrl("asdojasdsa/.sdfsdjfnj")
                    .build();



            em.persist(member);

            Member member2 = Member.builder()
                    .name("이영진")
                    .nickName("ㄴ빵")
                    .email("ㄱㄷㄴㄴㄷ@naver.com")
                    .password("rter141")
                    .imageUrl("asdㅌㅌㄴjasdsa/.sdfsdjfnj")
                    .build();

            em.persist(member2);

        }
    }

}
