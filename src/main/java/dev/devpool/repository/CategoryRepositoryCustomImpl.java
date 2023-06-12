package dev.devpool.repository;

import dev.devpool.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom{

    private final EntityManager em;

    @Override
    public Category findByTeamId(Long teamId) {
        Category category = em.createQuery("select cg from Category cg where cg.team.id=:teamId", Category.class)
                .setParameter("teamId", teamId)
                .getResultList()
                .stream()
                .findAny()
                .orElse(null);

        return category;
    }

    @Override
    public void deleteByTeamId(Long teamId) {
        Query query = em.createQuery("delete from Category cg where cg.team.id=:teamId")
                .setParameter("teamId", teamId);
        query.executeUpdate();
    }
}
