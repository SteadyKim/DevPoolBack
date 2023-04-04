package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import dev.devpool.domain.Stack;
import dev.devpool.domain.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StackServiceTest {
    @Autowired
    StackService stackService;

    @Autowired
    TeamService teamService;

    @Autowired
    MemberService memberService;

    @Autowired
    ProjectService projectService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

    @AfterEach
    public void 지우기() {
        System.out.println("======@AfterEach======");
        stackService.deleteAll();
    }

    @Test
    public void 스택저장조회() {
         transactionTemplate.execute(status -> {
             //given
             Stack stack = new Stack();

             //when
             stackService.join(stack);
             em.flush();
             em.clear();

             //then
             Stack findStack = stackService.findOneById(stack.getId());

             assertEquals(findStack.getId(), stack.getId());
             return null;
         });
    }

    @Test
    public void 팀아이디로스택조회() {
        transactionTemplate.execute(status -> {
            //given
            Stack stack = new Stack();
            Stack stack2 = new Stack();
            Team team = new Team();

            stack.setTeam(team);
            stack2.setTeam(team);

            //when
            teamService.join(team);
            stackService.join(stack);
            stackService.join(stack2);

            em.flush();
            em.clear();

            //then
            List<Stack> findStacks = stackService.findStacksByTeamId(team.getId());

            // 길이 체크
            assertEquals(2, findStacks.size());

            // 다들어있는지 체크
            ArrayList<Long> checkList = new ArrayList<>();
            checkList.add(stack.getId());
            checkList.add(stack2.getId());

            for (Stack findStack : findStacks) {
                assertTrue(checkList.contains(findStack.getId()));
            }

            return null;
        });
    }

    @Test
    public void 멤버아이디로스택조회() {
        transactionTemplate.execute(status -> {
            //given
            Stack stack = new Stack();
            Stack stack2 = new Stack();
            Member member = new Member();

            stack.setMember(member);
            stack2.setMember(member);

            //when
            memberService.join(member);
            stackService.join(stack);
            stackService.join(stack2);

            em.flush();
            em.clear();

            //then
            List<Stack> findStacks = stackService.findStacksByMemberId(member.getId());

            // 길이 체크
            assertEquals(2, findStacks.size());

            // 다들어있는지 체크
            ArrayList<Long> checkList = new ArrayList<>();
            checkList.add(stack.getId());
            checkList.add(stack2.getId());

            for (Stack findStack : findStacks) {
                assertTrue(checkList.contains(findStack.getId()));
            }

            return null;
        });
    }

    @Test
    public void 프로젝트아이디로스택조회() {
        transactionTemplate.execute(status -> {
            //given
            Stack stack = new Stack();
            Stack stack2 = new Stack();
            Project project = new Project();

            stack.setProject(project);
            stack2.setProject(project);



            //when
            projectService.join(project);
            stackService.join(stack);
            stackService.join(stack2);

            em.flush();
            em.clear();

            //then
            List<Stack> findStacks = stackService.findStacksByProjectId(project.getId());

            // 길이 체크
            assertEquals(2, findStacks.size());

            // 다들어있는지 체크
            ArrayList<Long> checkList = new ArrayList<>();
            checkList.add(stack.getId());
            checkList.add(stack2.getId());

            for (Stack findStack : findStacks) {
                assertTrue(checkList.contains(findStack.getId()));
            }

            return null;
        });
    }

    //삭제
    @Test
    public void 스택삭제() {
        transactionTemplate.execute(status -> {
            //given
            Stack stack = new Stack();
            stackService.join(stack);

            //when
            em.flush();
            em.clear();
            stackService.deleteById(stack.getId());
            em.flush();
            em.clear();

            //then

            Stack findStack = stackService.findOneById(stack.getId());
            assertNull(findStack);
            return null;
        });
    }


    @Test
    public void 팀아이디로삭제() {
        transactionTemplate.execute(status -> {
            //given
            Stack stack = new Stack();
            Stack stack2 = new Stack();
            Team team = new Team();

            stack.setTeam(team);
            stack2.setTeam(team);


            //when
            teamService.join(team);
            stackService.join(stack);
            stackService.join(stack2);

            em.flush();
            em.clear();

            //then
            stackService.deleteByTeamId(team.getId());
            em.flush();
            em.clear();

            List<Stack> findStacks = stackService.findStacksByTeamId(team.getId());

            assertEquals(findStacks.size(), 0);
            return null;
        });
    }

    @Test
    public void 멤버아이디로삭제() {
        transactionTemplate.execute(status -> {
            //given
            Stack stack = new Stack();
            Stack stack2 = new Stack();
            Member member = new Member();

            stack.setMember(member);
            stack2.setMember(member);


            //when
            memberService.join(member);
            stackService.join(stack);
            stackService.join(stack2);

            em.flush();
            em.clear();

            //then
            stackService.deleteByMemberId(member.getId());
            em.flush();
            em.clear();

            List<Stack> findStacks = stackService.findStacksByMemberId(member.getId());

            assertEquals(findStacks.size(), 0);
            return null;
        });
    }

    @Test
    public void 프로젝트아이디로삭제() {
        transactionTemplate.execute(status -> {
            //given
            Stack stack = new Stack();
            Stack stack2 = new Stack();
            Project project = new Project();

            stack.setProject(project);
            stack2.setProject(project);

            //when
            projectService.join(project);
            stackService.join(stack);
            stackService.join(stack2);

            em.flush();
            em.clear();

            //then
            stackService.deleteByProjectId(project.getId());
            em.flush();
            em.clear();

            List<Stack> findStacks = stackService.findStacksByProjectId(project.getId());

            assertEquals(findStacks.size(), 0);
            return null;
        });
    }

    //수정
    @Test
    public void 스택팀수정() {
        transactionTemplate.execute(status -> {
            //given
            Team team = new Team();
            teamService.join(team);

            Stack stack1 = new Stack();
            Stack stack2 = new Stack();
            stack1.setTeam(team);
            stack2.setTeam(team);

            stackService.join(stack1);
            stackService.join(stack2);

            //when
            Stack newStack1 = new Stack();
            Stack newStack2 = new Stack();

            ArrayList<Stack> stacks = new ArrayList<>();
            stacks.add(newStack1);
            stacks.add(newStack2);
            stackService.updateByTeam(team, stacks);

            em.flush();
            em.clear();

            //then
            List<Stack> findStacks = stackService.findStacksByTeamId(team.getId());

            // 길이
            assertEquals(findStacks.size(), 2);

            // 변화 체크
            ArrayList<Long> stackIds = new ArrayList<>();
            stackIds.add(newStack1.getId());
            stackIds.add(newStack2.getId());

            for (Stack findStack : findStacks) {
                assertTrue(stackIds.contains(findStack.getId()));
            }

            return null;
        });
    }

    @Test
    public void 스택멤버수정() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();
            memberService.join(member);

            Stack stack1 = new Stack();
            Stack stack2 = new Stack();
            stack1.setMember(member);
            stack2.setMember(member);

            stackService.join(stack1);
            stackService.join(stack2);

            //when
            Stack newStack1 = new Stack();
            Stack newStack2 = new Stack();

            ArrayList<Stack> stacks = new ArrayList<>();
            stacks.add(newStack1);
            stacks.add(newStack2);
            stackService.updateByMember(member, stacks);

            em.flush();
            em.clear();

            //then
            List<Stack> findStacks = stackService.findStacksByMemberId(member.getId());

            // 길이
            assertEquals(findStacks.size(), 2);

            // 변화 체크
            ArrayList<Long> stackIds = new ArrayList<>();
            stackIds.add(newStack1.getId());
            stackIds.add(newStack2.getId());

            for (Stack findStack : findStacks) {
                assertTrue(stackIds.contains(findStack.getId()));
            }

            return null;
        });
    }

    @Test
    public void 스택프로젝트수정() {
        transactionTemplate.execute(status -> {
            //given
            Project project = new Project();
            projectService.join(project);

            Stack stack1 = new Stack();
            Stack stack2 = new Stack();
            stack1.setProject(project);
            stack2.setProject(project);

            stackService.join(stack1);
            stackService.join(stack2);

            //when
            Stack newStack1 = new Stack();
            Stack newStack2 = new Stack();

            ArrayList<Stack> stacks = new ArrayList<>();
            stacks.add(newStack1);
            stacks.add(newStack2);
            stackService.updateByProject(project, stacks);

            em.flush();
            em.clear();

            //then
            List<Stack> findStacks = stackService.findStacksByProjectId(project.getId());

            // 길이
            assertEquals(findStacks.size(), 2);

            // 변화 체크
            ArrayList<Long> stackIds = new ArrayList<>();
            stackIds.add(newStack1.getId());
            stackIds.add(newStack2.getId());

            for (Stack findStack : findStacks) {
                assertTrue(stackIds.contains(findStack.getId()));
            }

            return null;
        });
    }
}