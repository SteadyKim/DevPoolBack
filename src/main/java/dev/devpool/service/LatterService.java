package dev.devpool.service;

import dev.devpool.domain.Certificate;
import dev.devpool.domain.Latter;
import dev.devpool.domain.Member;
import dev.devpool.repository.LatterRepository;
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
public class LatterService {
    private final LatterRepository latterRepository;

    private final MemberRepository memberRepository;


    // 저장
    @Transactional
    public Long join(Latter latter) {
        latterRepository.save(latter);

        return latter.getId();
    }

    // 조회
    public List<Latter> findAllByMemberId(Long memberId) {
        List<Latter> certificates = latterRepository.findAllByMemberId(memberId);

        return certificates;
    }

    public Latter findById(Long latterId) {
        Latter findLatter = latterRepository.findById(latterId);
        return findLatter;
    }

    // 삭제
    @Transactional
    public void deleteById(Long latterId) {
        latterRepository.deleteById(latterId);
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        latterRepository.deleteByMemberId(memberId);
    }
    @Transactional
    public void deleteAll() {
        latterRepository.deleteAll();
    }

    // 수정 - 로직 필요할 것같진 않음
//    @Transactional
//    public void update(Member member, ArrayList<Latter> latters) {
//        // 지우고
//        latterRepository.deleteByMemberId(member.getId());
//        // 추가
//        for (Latter latter : latters) {
//            member.addLatter(latter);
//        }
//    }
}
