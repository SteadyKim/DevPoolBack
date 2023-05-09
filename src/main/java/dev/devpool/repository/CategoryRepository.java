package dev.devpool.repository;

import dev.devpool.domain.Category;
import dev.devpool.exception.team.read.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class CategoryRepository {

    private final EntityManager em;

    @Autowired
    public CategoryRepository(EntityManager em) {
        this.em = em;
    }

    //저장
    public void save(Category category) {
        em.persist(category);
    }

    //조회
    public Category findByTeamId(Long teamId) {
        Category category = em.createQuery("select cg from Category cg where cg.team.id=:teamId", Category.class)
                .setParameter("teamId", teamId)
                .getResultList()
                .stream()
                .findAny()
                .orElse(null);

        return category;
    }

    //삭제
    public void deleteByTeamId(Long teamId) {
        Query query = em.createQuery("delete from Category cg where cg.team.id=:teamId")
                .setParameter("teamId", teamId);
        query.executeUpdate();
    }


}
