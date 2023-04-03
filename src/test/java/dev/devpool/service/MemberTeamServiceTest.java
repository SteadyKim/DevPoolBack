package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
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
            Member member = new Member();
            member.setName("김태우");
            member.setEmail("re@naver.com");
            member.setPassword("tae16");
            member.setNickName("귀요미");
            memberService.join(member);

            Member member2 = new Member();
            member2.setName("이영진");
            member2.setEmail("eloo@naver.com");
            member2.setPassword("taeu4616");
            member2.setNickName("귀요미2");
            memberService.join(member2);

            Team team = new Team();
            team.setBody("asdasdas");
            team.setTitle("A팀");
            team.setName("fsdf");
            team.setTotal_num(4);

            MemberTeam memberTeam = new MemberTeam();
            memberTeam.setMember(member);
            memberTeam.setTeam(team);

            MemberTeam memberTeam2 = new MemberTeam();
            memberTeam2.setMember(member2);
            memberTeam2.setTeam(team);

            team.getMemberTeams().add(memberTeam);
            team.getMemberTeams().add(memberTeam2);

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
            Member member = new Member();
            member.setName("김태우");
            member.setEmail("reres25@nar.com");
            member.setPassword("taeu16");
            member.setNickName("귀요미");
            memberService.join(member);

            Member member2 = new Member();
            member2.setName("이영진");
            member2.setEmail("eloo@naver.com");
            member2.setPassword("tae616");
            member2.setNickName("귀요미2");
            memberService.join(member2);

            Team team = new Team();
            team.setBody("asdasdas");
            team.setTitle("A팀");
            team.setName("fsdf");
            team.setTotal_num(4);

            MemberTeam memberTeam = new MemberTeam();
            memberTeam.setMember(member);
            memberTeam.setTeam(team);

            MemberTeam memberTeam2 = new MemberTeam();
            memberTeam2.setMember(member2);
            memberTeam2.setTeam(team);

            team.getMemberTeams().add(memberTeam);
            team.getMemberTeams().add(memberTeam2);

            teamService.join(team);

            //when
            em.flush();
            em.clear();
            System.out.println("=================");
            Team findTeam = teamService.findOne(team.getId());
            System.out.println("=================");
            teamService.delete(findTeam);
            System.out.println("=================");

            System.out.println(em.contains(findTeam));

            //then
            assertNull(teamService.findOne(team.getId())); // team도 없고
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
            Member member = new Member();
            member.setName("김태우");
            member.setEmail("reres25@nar.com");
            member.setPassword("taeu16");
            member.setNickName("귀요미");
            memberService.join(member);

            Member member2 = new Member();
            member2.setName("이영진");
            member2.setEmail("eloo@naver.com");
            member2.setPassword("tae616");
            member2.setNickName("귀요미2");
            memberService.join(member2);

            Member newMember1 = new Member();
            newMember1.setNickName("asda");
            newMember1.setName("asd");
            newMember1.setPassword("asda");
            newMember1.setEmail("asdasd");
            memberService.join(newMember1);

            Member newMember2 = new Member();
            newMember2.setNickName("asdasda");
            newMember2.setName("asasdasdd");
            newMember2.setPassword("aasdsda");
            newMember2.setEmail("asdaasdassd");
            memberService.join(newMember2);


            Team team = new Team();
            team.setBody("asdasdas");
            team.setTitle("A팀");
            team.setName("fsdf");
            team.setTotal_num(4);

            MemberTeam memberTeam = new MemberTeam();
            memberTeam.setMember(member);
            memberTeam.setTeam(team);

            MemberTeam memberTeam2 = new MemberTeam();
            memberTeam2.setMember(member2);
            memberTeam2.setTeam(team);

            team.getMemberTeams().add(memberTeam);
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
