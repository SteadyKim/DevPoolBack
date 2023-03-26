package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class TestService {
    private final EntityManager em;

    public TestService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void test() {
        Member member = new Member();
        member.setName("김태우");
        member.setEmail("rereers1125@naver.com");
        member.setPassword("taeu4616");
        member.setNickName("귀요미");
        em.persist(member);

        Team team = new Team();
        team.setBody("asdasdas");
        team.setTitle("A팀");
        team.setName("fsdf");
        team.setTotal_num(4);
        team.setRecruited_num(0);


        MemberTeam memberTeam = new MemberTeam();
        memberTeam.setMember(member);
        memberTeam.setTeam(team);

        team.getMemberTeams().add(memberTeam);


        em.persist(team);
        em.persist(memberTeam);
        /**
         * 순서에 따라 왜 바뀔까?
         */
    }
}
