package dev.devpool.service;

import dev.devpool.domain.Category;
import dev.devpool.domain.Team;
import dev.devpool.dto.CategoryDto;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.repository.CategoryRepository;
import dev.devpool.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final TeamRepository teamRepository;


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
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomEntityNotFoundException(Team.class.getName(), teamId));
        categoryRepository.deleteByTeamId(findTeam.getId());

        // 추가
        Category newCategory = Category.builder()
                .name(categoryDto.getName())
                .team(findTeam)
                .build();

        categoryRepository.save(newCategory);

    }
}
