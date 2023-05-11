package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.exception.CustomDuplicateException;
import dev.devpool.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    // 회원가입
    public Long join(Member member) {
        validateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    public void validateMember(Member member) {
        Optional<Member> findMember = memberRepository.findOneByEmail(member.getEmail());

        if (findMember.isPresent()) {
            throw new CustomDuplicateException(Member.class.getName(), member.getId());
        }
    }

    //조회
    public Member findOneById(Long memberId) {
        Member findMember = memberRepository.findOneById(memberId);

        return findMember;
    }

    public List<Member> findAll() {
        List<Member> members = memberRepository.findAll();

        return members;
    }

    @Transactional
    public void deleteById(Long memberId) {
    memberRepository.deleteById(memberId);
    }

    @Transactional
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    @Transactional
    public Member update(Long id, Member member) {
        Member findMember = memberRepository.findOneById(id);
        // 변경 감지 사용하기
        findMember.update(member);

        return findMember;
    }
}
