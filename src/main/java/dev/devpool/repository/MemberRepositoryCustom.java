package dev.devpool.repository;

import dev.devpool.domain.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {

    Optional<Member> findOneByEmail(String email);

    void deleteByMemberId(Long memberId);

    void deleteAllCustom();
}
