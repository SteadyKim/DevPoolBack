package dev.devpool.service;

import dev.devpool.domain.Member;
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
    public long join(Member member) {
        validateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    public void validateMember(Member member) {
        Optional<Member> findMember = memberRepository.findOneByEmail(member.getEmail());

        if (!(findMember.isEmpty())) {
            throw new IllegalArgumentException("이미 있는 회원입니다.");
        }
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

    @Transactional
    public void deleteById(long memberId) {
    memberRepository.deleteById(memberId);
    }

    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

    @Transactional
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    @Transactional
    public Member update(Long memberId, String name, String nickName, String mail, String password) {
        Member findMember = memberRepository.findOneById(memberId);
        // 변경 감지 사용하기
        findMember.setName(name);
        findMember.setNickName(nickName);
        findMember.setEmail(mail);
        findMember.setPassword(password);

        return findMember;
    }
}
