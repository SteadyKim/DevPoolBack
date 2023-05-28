package dev.devpool.repository;

import dev.devpool.domain.Latter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LatterRepository extends JpaRepository<Latter, Long>, LatterRepositoryCustom {
}
