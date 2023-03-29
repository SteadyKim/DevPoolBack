package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberTeamServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    TeamService teamService;

    @Autowired
    EntityManager em;

    @Test
    public void 멤버팀_저장_조회() {

        Member member = new Member();
        member.setName("김태우");
        member.setEmail("rereers1125@naver.com");
        member.setPassword("taeu4616");
        member.setNickName("귀요미");
        em.persist(member);

        Member member2 = new Member();
        member2.setName("이영진");
        member2.setEmail("eloo@naver.com");
        member2.setPassword("taeu4616");
        member2.setNickName("귀요미2");
        em.persist(member2);

        Team team = new Team();
        team.setBody("asdasdas");
        team.setTitle("A팀");
        team.setName("fsdf");
        team.setTotal_num(4);
        team.setRecruited_num(0);

        MemberTeam memberTeam = new MemberTeam();
        memberTeam.setMember(member);
        memberTeam.setTeam(team);

        MemberTeam memberTeam2 = new MemberTeam();
        memberTeam2.setMember(member2);
        memberTeam2.setTeam(team);

        team.getMemberTeams().add(memberTeam);
        team.getMemberTeams().add(memberTeam2);

        em.persist(team);

        em.flush();
        em.clear();

        Team findTeam = em.find(Team.class, team.getId());
        List<MemberTeam> memberTeams = findTeam.getMemberTeams();

        ArrayList<Long> memberIdList = new ArrayList<>();
        memberIdList.add(member.getId());
        memberIdList.add(member2.getId());

        for (MemberTeam memberTeam1 : memberTeams) {
            long id = memberTeam1.getMember().getId();
            assertTrue(memberIdList.contains(id));
        }
    }
}
