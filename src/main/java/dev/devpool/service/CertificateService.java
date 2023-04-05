package dev.devpool.service;

import dev.devpool.domain.Certificate;
import dev.devpool.repository.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }


    // 저장
    @Transactional
    public Long join(Certificate certificate) {
        certificateRepository.save(certificate);

        return certificate.getId();
    }

    // 조회
    public List<Certificate> findAllByMemberId(Long memberId) {
        List<Certificate> certificates = certificateRepository.findAllByMemberId(memberId);

        return certificates;
    }

    // 삭제
    public void deleteById(Long certificateId) {
        certificateRepository.deleteById(certificateId);
    }

    public void deleteAll() {
        certificateRepository.deleteAll();
    }
}
