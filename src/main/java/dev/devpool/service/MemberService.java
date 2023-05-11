package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.dto.CommonResponseDto;
import dev.devpool.dto.MemberDto;
import dev.devpool.exception.CustomDuplicateException;
import dev.devpool.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public CommonResponseDto<Object> join(MemberDto.Save memberDto) {
        Member member = memberDto.toEntity();

        validateMember(member);
        memberRepository.save(member);

        return CommonResponseDto.builder()
                .id(member.getId())
                .status(201)
                .message("회원 저장에 성공하였습니다.")
                .build();
    }

    public void validateMember(Member member) {
        Optional<Member> findMember = memberRepository.findOneByEmail(member.getEmail());

        if (findMember.isPresent()) {
            throw new CustomDuplicateException(Member.class.getName(), member.getId());
        }
    }

    //조회
    public MemberDto.Response findOneById(Long memberId) {
        Member findMember = memberRepository.findOneById(memberId);

        MemberDto.Response dto = findMember.toDto();
        return dto;
    }

    public List<MemberDto.Response> findAll() {
        List<MemberDto.Response> dtoList = memberRepository.findAll()
                .stream()
                .map(Member::toDto)
                .collect(Collectors.toList());

        return dtoList;
    }

    @Transactional
    public CommonResponseDto<Object> deleteById(Long memberId) {
    memberRepository.deleteById(memberId);

    return CommonResponseDto.builder()
            .status(200)
            .message("멤버 삭제에 성공하였습니다.")
            .id(memberId)
            .build();
    }

    @Transactional
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    @Transactional
    public CommonResponseDto<Object> update(Long id, MemberDto.Save memberDto) {
        Member findMember = memberRepository.findOneById(id);
        Member newMember = memberDto.toEntity();

        // 변경 감지 사용하기
        findMember.update(newMember);

        return CommonResponseDto.builder()
                .status(200)
                .id(id)
                .message("멤버 수정에 성공하였습니다.")
                .build();
    }
}
