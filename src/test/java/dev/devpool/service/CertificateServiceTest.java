package dev.devpool.service;

import dev.devpool.domain.Certificate;
import dev.devpool.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CertificateServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    CertificateService certificateService;

    @Autowired
    EntityManager em;

    @Autowired
    TransactionTemplate transactionTemplate;

//    @AfterEach
//    public void 지우기() {
//        System.out.println("======@AfterEach======");
//        memberService.deleteAll();
//    }

    @Test
    public void 자격증저장_조회ByMember() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();

            Certificate certificate = new Certificate();
            Certificate certificate2 = new Certificate();

            member.addCertificate(certificate);
            member.addCertificate(certificate2);


            memberService.join(member);
            certificateService.join(certificate);
            certificateService.join(certificate2);

            //when
            em.flush();
            em.clear();

            ArrayList<Long> ids = new ArrayList<>();
            ids.add(certificate.getId());
            ids.add(certificate2.getId());

            //then
            Member findMember = memberService.findOneById(member.getId());
            List<Certificate> findCertificates = certificateService.findAllByMemberId(findMember.getId());

            // 개수
            assertEquals(2, findCertificates.size());

            for (Certificate findCertificate : findCertificates) {
                assertTrue(ids.contains(findCertificate.getId()));
            }

            return null;
        });
    }

    @Test
    public void 자격증삭제ByMember() {
        transactionTemplate.execute(status -> {
            //given
            Member member = new Member();

            Certificate certificate = new Certificate();
            Certificate certificate2 = new Certificate();

            member.addCertificate(certificate);
            member.addCertificate(certificate2);

            memberService.join(member);
            certificateService.join(certificate);
            certificateService.join(certificate2);

            //when
            em.flush();
            em.clear();

            memberService.deleteById(member.getId());

            //then
            List<Certificate> findCertificates = certificateService.findAllByMemberId(member.getId());
            assertEquals(0, findCertificates.size());

            return null;
        });
    }

    @Test
    public void 자격증수정() {
        transactionTemplate.execute(status -> {
            // given
            Member member = new Member();
            Certificate certificate1 = new Certificate();
            Certificate certificate2 = new Certificate();
            Certificate certificate3 = new Certificate();
            Certificate certificate4 = new Certificate();

            ArrayList<Certificate> certificateArrayList = new ArrayList<>();
            certificateArrayList.add(certificate3);
            certificateArrayList.add(certificate4);

            member.addCertificate(certificate1);
            member.addCertificate(certificate2);

            memberService.join(member);
            certificateService.join(certificate1);
            certificateService.join(certificate2);

            // when
            ArrayList<Long> ids = new ArrayList<>();

            em.flush();
            em.clear();

            Member findMember = memberService.findOneById(member.getId());
            certificateService.update(findMember, certificateArrayList);

            em.flush();
            em.clear();

            ids.add(certificate3.getId());
            ids.add(certificate4.getId());

            // then
            Member findedMember = memberService.findOneById(member.getId());
            List<Certificate> certificates = findedMember.getCertificates();
            if (certificates.size() == 0){
                fail("값이 존재해야 합니다");
            }
            for (Certificate certificate : certificates) {
                assertTrue(ids.contains(certificate.getId()));
            }

            return null;
        });
    }
}