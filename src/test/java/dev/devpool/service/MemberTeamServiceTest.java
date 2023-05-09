package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
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
public class MemberTeamServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    TeamService teamService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;
    private MemberTeam memberTeam;

    @AfterEach
    public void deleteAll() {
        System.out.println("=====@AfterEach=====");
        teamService.deleteAll();
        memberService.deleteAll();
    }
    @Test
    public void 멤버팀_저장_조회() {

        transactionTemplate.execute(status -> {
            //given
            Member member = Member.builder()
                    .name("김태우")
                    .nickName("asd")
                    .email("reree1@naver.com")
                    .password("asdas")
                    .build();
            memberService.join(member);

            Member member2 = Member.builder()
                    .name("이영진")
                    .nickName("asㅁㄴㅇd")
                    .email("reree1@naㄴver.com")
                    .password("asdaㅁs")
                    .build();

            memberService.join(member2);

            Team team = Team.builder()
                    .name("A팀")
                    .body("asdasd")
                    .totalNum(4)
                    .build();

            MemberTeam memberTeam = new MemberTeam();
            memberTeam.addMemberTeam(member, team);

            MemberTeam memberTeam2 = new MemberTeam();
            memberTeam2.addMemberTeam(member2, team);

            teamService.join(team);
            em.flush();
            em.clear();

            //when
            Team findTeam = em.find(Team.class, team.getId());
            List<MemberTeam> memberTeams = findTeam.getMemberTeams();

            //then
            ArrayList<Long> memberIdList = new ArrayList<>();
            memberIdList.add(member.getId());
            memberIdList.add(member2.getId());

            for (MemberTeam memberTeam1 : memberTeams) {
                long id = memberTeam1.getMember().getId();
                assertTrue(memberIdList.contains(id));
            }
            return null;
        });

    }

    @Test
    public void 멤버_팀삭제 () {
        transactionTemplate.execute(status -> {
            //given
            Member member = Member.builder()
                    .name("김태우")
                    .nickName("asd")
                    .email("reree1@naver.com")
                    .password("asdas")
                    .build();
            memberService.join(member);

            Member member2 = Member.builder()
                    .name("이영진")
                    .nickName("asㅁㄴㅇd")
                    .email("reree1@naㄴver.com")
                    .password("asdaㅁs")
                    .build();
            memberService.join(member2);

            Team team = Team.builder()
                    .name("A팀")
                    .body("asdasd")
                    .totalNum(4)
                    .build();

            MemberTeam memberTeam = new MemberTeam();
            memberTeam.addMember(member);
            memberTeam.addTeam(team);

            MemberTeam memberTeam2 = new MemberTeam();
            memberTeam2.addMember(member2);
            memberTeam2.addTeam(team);

            team.getMemberTeams().add(memberTeam);
            team.getMemberTeams().add(memberTeam2);

            teamService.join(team);

            //when
            em.flush();
            em.clear();
            System.out.println("=================");
            Team findTeam = teamService.findOneById(team.getId());
            System.out.println("=================");
            teamService.deleteById(findTeam.getId());
            System.out.println("=================");

            em.flush();
            em.clear();

            //then
            assertNull(teamService.findOneById(findTeam.getId())); // team도 없고
//        select mt from MemberTeam mt where mt.team.id=:teamId
            List<MemberTeam> memberTeamList = em.createQuery("select mt from MemberTeam mt where mt.team.id=:teamId", MemberTeam.class)
                    .setParameter("teamId", team.getId())
                    .getResultList();
            assertEquals(memberTeamList.size(), 0); //Member_team도 없다.
            return null;

        });
    }
    @Test
    public void 멤버팀수정() {
        transactionTemplate.execute(status -> {
            //given
            Member member = Member.builder()
                    .name("김태우")
                    .nickName("asd")
                    .email("reree1@naver.com")
                    .password("asdas")
                    .build();
            memberService.join(member);

            Member member2 = Member.builder()
                    .name("이영진")
                    .nickName("asㅁㄴㅇd")
                    .email("reree1@naㄴver.com")
                    .password("asdaㅁs")
                    .build();
            memberService.join(member2);


            Member newMember1 = Member.builder()
                    .name("asasdas")
                    .nickName("asdsa")
                    .email("assadd")
                    .password("asd")
                    .build();

            memberService.join(newMember1);


            Member newMember2 = Member.builder()
                    .name("asasdas")
                    .nickName("asdsa")
                    .email("asasdsadd")
                    .password("asd")
                    .build();

            memberService.join(newMember2);


            Team team = Team.builder()
                    .name("A팀")
                    .body("asdasd")
                    .totalNum(4)
                    .build();


            MemberTeam memberTeam1 = MemberTeam.builder()
                    .build();
            memberTeam1.addMemberTeam(member, team);

            MemberTeam memberTeam2 = MemberTeam.builder()
                    .build();

            memberTeam2.addMemberTeam(member2, team);

            team.getMemberTeams().add(memberTeam1);
            team.getMemberTeams().add(memberTeam2);

            teamService.join(team);

            //when
            em.flush();
            em.clear();


            System.out.println("=====================");
            //then
            teamService.updateMemberTeam(team.getId(), newMember1, newMember2);



            return null;
        });
    }

}
