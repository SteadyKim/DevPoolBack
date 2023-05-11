package dev.devpool.service;

import dev.devpool.domain.*;
import dev.devpool.dto.*;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberPoolService {

        private final MemberRepository memberRepository;

        private final TechFieldRepository techFieldRepository;

        private final StackRepository stackRepository;

        private final ProjectRepository projectRepository;

        private final CertificateRepository certificateRepository;

        private final SiteRepository siteRepository;


        @Transactional
        public CommonResponseDto<Object> join(MemberPoolDto.Save memberPoolDto) {
                Long memberId = memberPoolDto.getMemberId();
                Member findMember = memberRepository.findOneById(memberId);
                findMember.setMemberPoolCreateTime(LocalDateTime.now());

                List<TechFieldDto.Save> techFieldDtoList = memberPoolDto.getTechFieldDtoList();
                for (TechFieldDto.Save techFieldDto : techFieldDtoList) {
                        TechField techField = techFieldDto.toEntity(findMember);
                        techFieldRepository.save(techField);
                }

                List<StackDto.Save> stackDtoList = memberPoolDto.getStackDtoList();

                for (StackDto.Save stackDto : stackDtoList) {
                        Stack stack = stackDto.toEntity(findMember);
                        stackRepository.save(stack);
                }

                List<ProjectDto.Save> projectDtoList = memberPoolDto.getProjectDtoList();

                for (ProjectDto.Save projectDto : projectDtoList) {

                        Project project = projectDto.toEntity(findMember);
                        projectRepository.save(project);
                }


                List<CertificateDto.Save> certificateDtoList = memberPoolDto.getCertificateDtoList();
                for (CertificateDto.Save certificateDto : certificateDtoList) {
                        Certificate certificate = certificateDto.toEntity(findMember);
                        certificateRepository.save(certificate);
                }

                List<SiteDto.Save> siteDtoList = memberPoolDto.getSiteDtoList();
                for (SiteDto.Save siteDto : siteDtoList) {
                        Site site = siteDto.toEntity(findMember);
                        siteRepository.save(site);
                }

        return CommonResponseDto.builder()
                .status(201)
                .message("멤버 풀 저장에 성공하였습니다.")
                .build();
}

        public MemberPoolDto.Response findOneById(Long memberId) {

                Member findMember = memberRepository.findOneById(memberId);

                if (findMember.getCreateTime() == null) {
                        throw new CustomEntityNotFoundException("MemberPool", memberId);
                }
                List<TechFieldDto.Response> techFieldDtoList = techFieldRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(TechField::toDto)
                        .collect(Collectors.toList());

                List<StackDto.Response> stackDtoList = stackRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(Stack::toDto)
                        .collect(Collectors.toList());

                List<ProjectDto.Response> projectDtoList = projectRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(Project::toDto)
                        .collect(Collectors.toList());

                List<CertificateDto.Response> certificateDtoList = certificateRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(Certificate::toDto)
                        .collect(Collectors.toList());

                List<SiteDto.Response> siteDtoList = siteRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(Site::toDto)
                        .collect(Collectors.toList());

                MemberPoolDto.Response memberPoolResponseDto = MemberPoolDto.Response.builder()
                        .memberId(memberId)
                        .createTime(findMember.getCreateTime())
                        .nickName(findMember.getNickName())
                        .email(findMember.getEmail())
                        .imageUrl(findMember.getImageUrl())
                        .techFieldDtoList(techFieldDtoList)
                        .stackDtoList(stackDtoList)
                        .projectDtoList(projectDtoList)
                        .certificateDtoList(certificateDtoList)
                        .siteDtoList(siteDtoList)
                        .build();

                return memberPoolResponseDto;

        }

        public List<MemberPoolDto.Response> findMemberPools() {
                List<Member> memberList = memberRepository.findAll()
                        .stream()
                        .filter(s -> s.getCreateTime() != null)
                        .sorted(Comparator.comparing(Member::getCreateTime))
                        .collect(Collectors.toList());

                List<MemberPoolDto.Response> memberPoolDtoList = new ArrayList<>();

                for (Member findMember : memberList) {
                        Long memberId = findMember.getId();
                        MemberPoolDto.Response memberPoolResponseDto = findOneById(memberId);
                        memberPoolDtoList.add(memberPoolResponseDto);
                }

                return memberPoolDtoList;
        }

        @Transactional
        public CommonResponseDto<Object> deleteById(Long memberId) {
                Member findMember = memberRepository.findOneById(memberId);
                findMember.setMemberPoolCreateTime(null);
                techFieldRepository.deleteAllByMemberId(memberId);
                stackRepository.deleteAllByMemberId(memberId);
                projectRepository.deleteByMemberId(memberId);
                certificateRepository.deleteAllByMemberId(memberId);
                siteRepository.deleteAllByMemberId(memberId);

                return CommonResponseDto.builder()
                        .status(200)
                        .message("멤버 풀 삭제에 성공하였습니다.")
                        .id(memberId)
                        .build();
        }

        @Transactional
        public CommonResponseDto<Object> update(MemberPoolDto.Save memberPoolDto) {
                Long memberId = memberPoolDto.getMemberId();
                /**
                 * 삭제
                 */
                deleteById(memberId);

                /**
                 * 삽입
                 */
                join(memberPoolDto);

                return CommonResponseDto.builder()
                        .status(200)
                        .message("멤버 풀 수정에 성공하였습니다.")
                        .id(memberId)
                        .build();
        }
}
