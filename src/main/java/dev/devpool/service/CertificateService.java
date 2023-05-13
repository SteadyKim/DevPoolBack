package dev.devpool.service;

import dev.devpool.domain.Certificate;
import dev.devpool.domain.Member;
import dev.devpool.repository.CertificateRepository;
import dev.devpool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CertificateService {

    private final CertificateRepository certificateRepository;

    private final MemberRepository memberRepository;



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
        certificateRepository.deleteAllByMemberId(memberId);
    }
    @Transactional
    public void deleteAll() {
        certificateRepository.deleteAll();
    }

    // 수정
    @Transactional
    public void update(Long memberId, ArrayList<String> certificateNameList) {
        // 지우고
        /**
         * 리팩 해야함
         */
        Member findMember = memberRepository.findOneById(memberId);

        certificateRepository.deleteAllByMemberId(findMember.getId());
        // 추가
        for (String certificateName : certificateNameList) {
            Certificate certificate = Certificate.builder()
                    .name(certificateName)
                    .member(findMember)
                    .build();

            certificateRepository.save(certificate);
        }


    }
}


