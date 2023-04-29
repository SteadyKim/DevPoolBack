package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import dev.devpool.domain.Stack;
import dev.devpool.domain.Team;
import dev.devpool.repository.StackRepository;
import dev.devpool.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;
    private final TeamRepository teamRepository;

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

    public List<Stack> findAllByTeamId(Long teamId){
        return stackRepository.findStacksByTeamId(teamId);
    };

    public List<Stack> findAllByMemberId(Long memberId){
        return stackRepository.findStacksByMemberId(memberId);
    };
    public List<Stack> findAllByProjectId(Long projectId){
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
    public void updateByTeam(Long teamId, List<String> stackNameList) {
        Team findTeam = teamRepository.findOneById(teamId);

        // 지우기
        stackRepository.deleteByTeamId(findTeam.getId());

        // 추가
        for (String stackName : stackNameList) {
            Stack stack = Stack.builder()
                    .team(findTeam)
                    .name(stackName)
                    .build();

            stackRepository.save(stack);
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
