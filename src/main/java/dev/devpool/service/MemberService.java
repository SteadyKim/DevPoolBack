package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.parameter.MemberParameter;
import dev.devpool.dto.common.CommonResponseDto;
import dev.devpool.dto.MemberDto;
import dev.devpool.exception.CustomDuplicateException;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.jwt.JwtTokenProvider;
import dev.devpool.jwt.TokenInfo;
import dev.devpool.repository.MemberRepository;
import dev.devpool.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final S3Uploader s3Uploader;

    @Transactional
    // 로그인
    public TokenInfo login(String email, String password){

        // 사용자가 아이디와 비밀번호를 입력하면 UsernamePasswordAuthentication 토큰을 생성한다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분

        AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();

        // authentication 토큰을 AuthenticationManager가 받아 해당 유저에 대한 검증을 한다.
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행되도록 설정되어 있다.

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        Member findMember = memberRepository.findOneByEmail(email)
                .orElseThrow(() -> new CustomEntityNotFoundException(Member.class.getName(), 0L));

        Long memberId = findMember.getId();


        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication, memberId);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성

        return tokenInfo;

    }

    @Transactional
    // 회원가입
    public CommonResponseDto<Object> join(MemberParameter memberParameter) throws IOException {
        String storeFileName = "https://devpoolback.s3.ap-northeast-2.amazonaws.com/images/default.png";

        MultipartFile image = memberParameter.getImage();
        System.out.println("image = " + image);

        if(!(image == null) && !(image.isEmpty())) {
            storeFileName = s3Uploader.upload(image, "images");
        }

        Member member = Member.builder()
                .name(memberParameter.getName())
                .BJId(memberParameter.getBJId())
                .email(memberParameter.getEmail())
                .nickName(memberParameter.getNickName())
                .password(memberParameter.getPassword())
                .imageUrl(storeFileName)
                .roles(List.of("USER"))
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
        Optional<Member>  findMember = memberRepository.findOneByEmail(member.getEmail());

        if (findMember.isPresent()) {
            throw new CustomDuplicateException(Member.class.getName(), 1L);
        }
    }

    //조회
    public MemberDto.Response findOneById(Long memberId) {
        Member findMember = memberRepository.findOneById(memberId);

        MemberDto.Response responseDto = MemberDto.Response.builder()
                .memberId(findMember.getId())
                .BJId(findMember.getBJId())
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
                        .BJId(member.getBJId())
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
    public CommonResponseDto<Object> update(Long id, MemberDto.Save memberDto, MultipartFile image) throws IOException {
        Member findMember = memberRepository.findOneById(id);
        String storeFileName = null;

        if(!image.isEmpty()) {
            storeFileName = s3Uploader.upload(image, "images");
        }
        // 변경 감지 사용하기
        findMember.update(memberDto, storeFileName);

        return CommonResponseDto.builder()
                .status(200)
                .id(id)
                .message("멤버 수정에 성공하였습니다.")
                .build();
    }

    public void updateImage(MultipartFile image) throws IOException {
        s3Uploader.upload(image, "images");
    }
}
