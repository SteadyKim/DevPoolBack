package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import dev.devpool.domain.TechField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TechFieldServiceTest {
    @Autowired
    TechFieldService techFieldService;

    @Autowired
    MemberService memberService;

    @Autowired
    TeamService teamService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

    @AfterEach
    public void 지우기() {
        System.out.println("======@AfterEach======");
        techFieldService.deleteAll();
    }

    @Test
    public void 기술분야저장_조회() {
        transactionTemplate.execute(status -> {
            //given
            TechField techField = new TechField();
            techFieldService.join(techField);

            //when
            em.flush();
            em.clear();

            TechField findTechField = techFieldService.findById(techField.getId());

            //then
            assertEquals(techField.getId(), findTechField.getId());

            return null;
        });
    }

    @Test
    public void 기술분야저장_조회ByMember() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();

            TechField techField1 = TechField.builder()
                    .member(member)
                    .build();
            TechField techField2 = TechField.builder()
                    .member(member)
                    .build();

            memberService.join(member);

            techFieldService.join(techField1);
            techFieldService.join(techField2);

            //when
            em.flush();
            em.clear();

            List<TechField> findTechFields = techFieldService.findAllByMemberId(member.getId());
            ArrayList<Long> ids = new ArrayList<Long>();
            ids.add(techField1.getId());
            ids.add(techField2.getId());

            //then
            assertEquals(2, findTechFields.size());

            for (TechField findTechField : findTechFields) {
                assertTrue(ids.contains(findTechField.getId()));
            }

            return null;
        });
    }

    @Test
    public void 기술분야저장_조회ByTeam() {
        transactionTemplate.execute(status -> {
            //given
            Team team = new Team();


            TechField techField1 = TechField.builder()
                    .team(team)
                    .build();

            TechField techField2 = TechField.builder()
                    .team(team)
                    .build();


            teamService.join(team);

            techFieldService.join(techField1);
            techFieldService.join(techField2);

            //when
            em.flush();
            em.clear();

            List<TechField> findTechFields = techFieldService.findAllByTeamId(team.getId());
            ArrayList<Long> ids = new ArrayList<Long>();
            ids.add(techField1.getId());
            ids.add(techField2.getId());

            //then
            assertEquals(2, findTechFields.size());

            for (TechField findTechField : findTechFields) {
                assertTrue(ids.contains(findTechField.getId()));
            }

            return null;
        });
    }

    @Test
    public void 기술분야삭제() {
        transactionTemplate.execute(status -> {
            //given
            TechField techField = new TechField();
            techFieldService.join(techField);

            //when
            em.flush();
            em.clear();

            techFieldService.deleteById(techField.getId());
            em.flush();
            em.clear();


            //then
            TechField findTechField = techFieldService.findById(techField.getId());

            assertNull(findTechField);

            return null;
        });
    }

    @Test
    public void 기술분야삭제ByMember() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();
            TechField techField1 = new TechField();
            TechField techField2 = new TechField();

            memberService.join(member);

            techFieldService.join(techField1);
            techFieldService.join(techField2);

            //when
            em.flush();
            em.clear();

            techFieldService.deleteAllByMemberId(member.getId());
            em.flush();
            em.clear();


            //then
            List<TechField> findTechFields = techFieldService.findAllByMemberId(member.getId());

            assertEquals(0, findTechFields.size());
            return null;
        });
    }

    @Test
    public void 기술분야삭제ByTeam() {
        transactionTemplate.execute(status -> {
            //given
            Team team  = new Team();
            TechField techField1 = new TechField();
            TechField techField2 = new TechField();

            teamService.join(team);

            techFieldService.join(techField1);
            techFieldService.join(techField2);

            //when
            em.flush();
            em.clear();

            techFieldService.deleteAllByTeamId(team.getId());

            em.flush();
            em.clear();


            //then
            List<TechField> findTechFields = techFieldService.findAllByTeamId(team.getId());

            assertEquals(0, findTechFields.size());
            return null;
        });
    }

    @Test
    public void 기술분야수정WithMember() {
        transactionTemplate.execute(status -> {
            //given

            Member member = new Member();
            TechField techField1 = new TechField();
            TechField techField2 = new TechField();

            memberService.join(member);

            techFieldService.join(techField1);
            techFieldService.join(techField2);

            //when
            TechField techField3 = new TechField();
            TechField techField4 = new TechField();
            ArrayList<String> techFieldNames = new ArrayList<>();
            techFieldNames.add(techField3.getName());
            techFieldNames.add(techField4.getName());

            em.flush();
            em.clear();

            Member findMember = memberService.findOneById(member.getId());
            techFieldService.updateByMember(findMember.getId(), techFieldNames);


            em.flush();
            em.clear();

            //then
            List<TechField> findTechFields = techFieldService.findAllByMemberId(findMember.getId());
            for (TechField findTechField : findTechFields) {
                assertTrue(techFieldNames.contains(findTechField.getName()));
            }

            return null;
        });
    }























}