package dev.devpool.repository;

import dev.devpool.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> , MemberRepositoryCustom {

}
