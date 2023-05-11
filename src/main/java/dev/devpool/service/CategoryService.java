package dev.devpool.service;

import dev.devpool.domain.Category;
import dev.devpool.domain.Certificate;
import dev.devpool.domain.Team;
import dev.devpool.dto.CategoryDto;
import dev.devpool.repository.CategoryRepository;
import dev.devpool.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final TeamRepository teamRepository;


    public CategoryService(CategoryRepository categoryRepository, TeamRepository teamRepository) {
        this.categoryRepository = categoryRepository;
        this.teamRepository = teamRepository;
    }

    // 저장
    @Transactional
    public Long join(Category category) {
        categoryRepository.save(category);

        return category.getId();
    }

    // 삭제
    @Transactional
    public void deleteByTeamId(Long teamId) {
        categoryRepository.deleteByTeamId(teamId);
    }

    public Category findOneByTeamId(Long teamId) {
        Category category = categoryRepository.findByTeamId(teamId);
        return category;
    }


    // 수정
    @Transactional
    public void update(Long teamId, CategoryDto.Save categoryDto) {
        // 지우고
        Team findTeam = teamRepository.findOneById(teamId);
        categoryRepository.deleteByTeamId(findTeam.getId());

        // 추가
        Category newCategory = categoryDto.toEntity(findTeam);

        categoryRepository.save(newCategory);

    }
}
