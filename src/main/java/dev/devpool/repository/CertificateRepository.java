package dev.devpool.repository;

import dev.devpool.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long>, CertificateRepositoryCustom {
}
