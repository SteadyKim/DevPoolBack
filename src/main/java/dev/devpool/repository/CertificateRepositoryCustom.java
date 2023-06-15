package dev.devpool.repository;

import dev.devpool.domain.Certificate;

import java.util.List;

public interface CertificateRepositoryCustom {

    List<Certificate> findAllByMemberId(Long memberId);


    void deleteAllByMemberId(Long memberId);
}
