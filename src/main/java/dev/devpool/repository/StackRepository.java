package dev.devpool.repository;

import dev.devpool.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackRepository extends JpaRepository<Stack, Long>, StackRepositoryCustom {
}
