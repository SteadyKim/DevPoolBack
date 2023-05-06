package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import dev.devpool.domain.Stack;
import dev.devpool.domain.Team;
import dev.devpool.repository.MemberRepository;
import dev.devpool.repository.ProjectRepository;
import dev.devpool.repository.StackRepository;
import dev.devpool.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StackService {

    private final StackRepository stackRepository;
    private final TeamRepository teamRepository;

    private final MemberRepository memberRepository;

    private final ProjectRepository projectRepository;

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
        return stackRepository.findAllByTeamId(teamId);
    };

    public List<Stack> findAllByMemberId(Long memberId){
        return stackRepository.findAllByMemberId(memberId);
    };
    public List<Stack> findAllByProjectId(Long projectId){
        return stackRepository.findAllByProjectId(projectId);
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
        stackRepository.deleteAllByTeamId(teamId);
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        stackRepository.deleteAllByMemberId(memberId);
    }

    @Transactional
    public void deleteByProjectId(Long projectId) {
        stackRepository.deleteAllByProjectId(projectId);
    }


    // 수정
    @Transactional
    public void updateByTeam(Long teamId, List<String> stackNameList) {
        Team findTeam = teamRepository.findOneById(teamId);

        // 지우기
        stackRepository.deleteAllByTeamId(findTeam.getId());

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
    public void updateByMember(Long memberId, List<String> StackNameList) {
        // 지우기
        stackRepository.deleteAllByMemberId(memberId);

        Member member = memberRepository.findOneById(memberId);

        // 추가
        for (String stackName : StackNameList) {
            Stack stack = Stack.builder()
                    .name(stackName)
                    .member(member)
                    .build();

            stackRepository.save(stack);
        }
    }

    @Transactional
    public void updateByProject(Long projectId, List<String> stackNameList) {
        // 지우기
        stackRepository.deleteAllByProjectId(projectId);

        // 추가
        Project project = projectRepository.findOneById(projectId);
        for (String stackName : stackNameList) {
            Stack stack = Stack.builder()
                    .name(stackName)
                    .project(project)
                    .build();
            stackRepository.save(stack);
        }
    }
}
