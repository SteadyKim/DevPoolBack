package dev.devpool.service;

import dev.devpool.domain.Certificate;
import dev.devpool.domain.Member;
import dev.devpool.repository.CertificateRepository;
import dev.devpool.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CertificateService {

    private final CertificateRepository certificateRepository;

    private final MemberRepository memberRepository;

    public CertificateService(CertificateRepository certificateRepository, MemberRepository memberRepository) {
        this.certificateRepository = certificateRepository;
        this.memberRepository = memberRepository;
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
    @Transactional
    public void deleteById(Long certificateId) {
        certificateRepository.deleteById(certificateId);
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        certificateRepository.deleteByMemberId(memberId);
    }
    @Transactional
    public void deleteAll() {
        certificateRepository.deleteAll();
    }

    // 수정
    @Transactional
    public void update(Member member, ArrayList<Certificate> certificates) {
        // 지우고
        certificateRepository.deleteByMemberId(member.getId());
        // 추가
        for (Certificate certificate : certificates) {
            member.addCertificate(certificate);
        }
    }


}
