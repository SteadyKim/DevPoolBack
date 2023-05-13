package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.dto.CommonResponseDto;
import dev.devpool.dto.MemberDto;
import dev.devpool.exception.CustomDuplicateException;
import dev.devpool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;


    @Transactional
    // 회원가입
    public CommonResponseDto<Object> join(MemberDto.Save memberDto) {
        Member member = Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .imageUrl(memberDto.getImageUrl())
                .build();


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

        MemberDto.Response responseDto = MemberDto.Response.builder()
                .memberId(findMember.getId())
                .name(findMember.getName())
                .nickName(findMember.getNickName())
                .email(findMember.getEmail())
                .imageUrl(findMember.getImageUrl())
                .build();

        return responseDto;
    }

    public List<MemberDto.Response> findAll() {
        List<MemberDto.Response> dtoList = memberRepository.findAll()
                .stream()
                .map(member -> MemberDto.Response.builder()
                        .memberId(member.getId())
                        .name(member.getName())
                        .nickName(member.getNickName())
                        .email(member.getEmail())
                        .imageUrl(member.getImageUrl())
                        .build()
                )
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

        // 변경 감지 사용하기
        findMember.update(memberDto);

        return CommonResponseDto.builder()
                .status(200)
                .id(id)
                .message("멤버 수정에 성공하였습니다.")
                .build();
    }
}
