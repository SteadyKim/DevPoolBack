package dev.devpool.service;

import dev.devpool.domain.Project;
import dev.devpool.dto.ProjectDto;
import dev.devpool.dto.StackDto;
import dev.devpool.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;



    // 저장
    @Transactional
    public Long join(Project project) {
        projectRepository.save(project);

        return project.getId();
    }

    // 조회
    public ProjectDto.Response findOneById(Long projectId) {
        Project findProject = projectRepository.findById(projectId)
                .orElse(null);

        if(findProject == null) return null;

        ProjectDto.Response projectDto = getProjectDto(findProject);

        return projectDto;
    }

    private static ProjectDto.Response getProjectDto(Project findProject) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        LocalDate startDate = findProject.getStartDate();
        String startDateString = startDate.format(formatter);

        LocalDate endDate = findProject.getEndDate();
        String endDateString = endDate.format(formatter);

        ProjectDto.Response projectDto = ProjectDto.Response
                .builder()
                .name(findProject.getName())
                .startDate(startDateString)
                .endDate(endDateString)
                .stack(findProject.getStackList().stream().map(stack -> StackDto.Response.builder()
                                .name(stack.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        return projectDto;
    }

    public List<ProjectDto.Response> findProjects() {

        List<ProjectDto.Response> projectDtoList = projectRepository.findAll().stream()
                .map(ProjectService::getProjectDto)
                .collect(Collectors.toList());

        return projectDtoList;
    }

    public List<ProjectDto.Response> findAllByMemberId(Long memberId) {
        List<Project> findProjectList = projectRepository.findAllByMemberId(memberId);
        List<ProjectDto.Response> projectDtoList = findProjectList.stream()
                .map(ProjectService::getProjectDto)
                .collect(Collectors.toList());

        return projectDtoList;
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
