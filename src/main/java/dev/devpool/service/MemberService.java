package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public long join(Member member) {
        memberRepository.save(member);

        return member.getId();
    }

    //조회
    public Member findOne(long memberId) {
        Member findMember = memberRepository.findOneById(memberId);

        return findMember;
    }

    public List<Member> findMembers() {
        List<Member> members = memberRepository.findAll();

        return members;
    }

    public void deleteById(long memberId) {
    memberRepository.deleteById(memberId);
    }

    public void delete(Member member) {
        memberRepository.delete(member);
    }

    public void deleteAll() {
        memberRepository.deleteAll();
    }
}
