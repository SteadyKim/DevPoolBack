package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import dev.devpool.domain.Stack;
import dev.devpool.domain.Team;
import dev.devpool.repository.StackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class StackService {

    private final StackRepository stackRepository;

    public StackService(StackRepository stackRepository) {
        this.stackRepository = stackRepository;
    }

    @Transactional
    // 저장
    public Long join(Stack stack) {
        stackRepository.save(stack);

        return stack.getId();
    }

    //조회
    public Stack findOneById(Long stackId) {
        return stackRepository.findOneById(stackId);
    }

    public List<Stack> findStacksByTeamId(Long teamId){
        return stackRepository.findStacksByTeamId(teamId);
    };

    public List<Stack> findStacksByMemberId(Long memberId){
        return stackRepository.findStacksByMemberId(memberId);
    };
    public List<Stack> findStacksByProjectId(Long projectId){
        return stackRepository.findStacksByProjectId(projectId);
    };


    // 삭제

    @Transactional
    public void deleteById(Long stackId) {
        stackRepository.deleteById(stackId);
    }

    @Transactional
    public void deleteAll() {
        stackRepository.deleteAll();
    }

    @Transactional
    public void deleteByTeamId(Long teamId) {
        stackRepository.deleteByTeamId(teamId);
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        stackRepository.deleteByMemberId(memberId);
    }

    @Transactional
    public void deleteByProjectId(Long projectId) {
        stackRepository.deleteByProjectId(projectId);
    }


    // 수정
    @Transactional
    public void updateByTeam(Team team, ArrayList<Stack> newStacks) {
        // 지우기
        stackRepository.deleteByTeamId(team.getId());

        // 추가
        for (Stack newStack : newStacks) {
            newStack.setTeam(team);
            stackRepository.save(newStack);
        }
    }


    @Transactional
    public void updateByMember(Member member, ArrayList<Stack> newStacks) {
        // 지우기
        stackRepository.deleteByMemberId(member.getId());

        // 추가
        for (Stack newStack : newStacks) {
            newStack.setMember(member);
            stackRepository.save(newStack);
        }
    }

    @Transactional
    public void updateByProject(Project project, ArrayList<Stack> newStacks) {
        // 지우기
        stackRepository.deleteByProjectId(project.getId());

        // 추가
        for (Stack newStack : newStacks) {
            newStack.setProject(project);
            stackRepository.save(newStack);
        }
    }
}
