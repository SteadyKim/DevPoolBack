package dev.devpool.repository;

import dev.devpool.domain.Category;
import dev.devpool.domain.TechField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechFieldRepository extends JpaRepository<TechField, Long>, TechFieldRepositoryCustom  {
}
