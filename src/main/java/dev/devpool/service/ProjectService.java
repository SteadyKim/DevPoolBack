package dev.devpool.service;

import dev.devpool.domain.Project;
import dev.devpool.domain.Stack;
import dev.devpool.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    // 저장
    @Transactional
    public Long join(Project project) {
        projectRepository.save(project);

        return project.getId();
    }

    // 조회
    public Project findOneById(Long projectId) {
        Project findProject = projectRepository.findOneById(projectId);
        return findProject;
    }

    public List<Project> findProjects() {
        List<Project> findProjects = projectRepository.findAll();
        return findProjects;
    }

    public List<Project> findProjectsByMemberId(Long memberId) {
        List<Project> findProjects = projectRepository.findAllByMemberId(memberId);
        return findProjects;
    }

    // 삭제

    @Transactional
    public void deleteById(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Transactional
    public void deleteAll() {
        projectRepository.deleteAll();
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        projectRepository.deleteByMemberId(memberId);
    }

    // 수정
}
