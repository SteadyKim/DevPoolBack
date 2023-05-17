package dev.devpool.service;

import dev.devpool.domain.*;
import dev.devpool.dto.*;
import dev.devpool.dto.common.CommonResponseDto;
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

                List<TechFieldDto.Save> techFieldDtoList = memberPoolDto.getTechField();
                for (TechFieldDto.Save techFieldDto : techFieldDtoList) {

                        TechField techField = TechField.builder()
                                .member(findMember)
                                .name(techFieldDto.getName())
                                .build();

                        techFieldRepository.save(techField);
                }

                List<StackDto.Save> stackDtoList = memberPoolDto.getStack();

                for (StackDto.Save stackDto : stackDtoList) {
                        Stack stack = Stack.builder()
                                .member(findMember)
                                .name(stackDto.getName())
                                .build();
                        stackRepository.save(stack);
                }

                List<ProjectDto.Save> projectDtoList = memberPoolDto.getProject();

                for (ProjectDto.Save projectDto : projectDtoList) {

                        Project project = Project.builder()
                                .member(findMember)
                                .url(projectDto.getUrl())
                                .startDate(projectDto.getStartDate())
                                .endDate(projectDto.getEndDate())
                                .build();
                        projectRepository.save(project);

                        List<StackDto.Save> projectStackDtoList = projectDto.getStack();
                        for (StackDto.Save projectStackDto : projectStackDtoList) {
                                Stack stack = Stack.builder()
                                        .project(project)
                                        .name(projectStackDto.getName())
                                        .build();

                                stackRepository.save(stack);
                        }
                }


                List<CertificateDto.Save> certificateDtoList = memberPoolDto.getCertificate();
                for (CertificateDto.Save certificateDto : certificateDtoList) {
                        Certificate certificate = Certificate.builder()
                                .member(findMember)
                                .name(certificateDto.getName())
                                .build();

                        certificateRepository.save(certificate);
                }

                List<SiteDto.Save> siteDtoList = memberPoolDto.getSite();
                for (SiteDto.Save siteDto : siteDtoList) {
                        Site site = Site.builder()
                                .url(siteDto.getUrl())
                                .name(siteDto.getName())
                                .member(findMember)
                                .build();

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
                        .map(techField -> TechFieldDto.Response.builder()
                                .name(techField.getName())
                                .build())
                        .collect(Collectors.toList());

                List<StackDto.Response> stackDtoList = stackRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(stack -> getStackDto(stack))
                        .collect(Collectors.toList());

                List<ProjectDto.Response> projectDtoList = projectRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(project -> getProjectDto(project))
                        .collect(Collectors.toList());

                List<CertificateDto.Response> certificateDtoList = certificateRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(certificate ->
                                getCertificateDto(certificate))
                        .collect(Collectors.toList());

                List<SiteDto.Response> siteDtoList = siteRepository.findAllByMemberId(memberId)
                        .stream()
                        .map(site -> getSiteDto(site))
                        .collect(Collectors.toList());

                MemberPoolDto.Response memberPoolResponseDto = getMemberPoolResponseDto(memberId, findMember, techFieldDtoList, stackDtoList, projectDtoList, certificateDtoList, siteDtoList);

                return memberPoolResponseDto;

        }

        private static SiteDto.Response getSiteDto(Site site) {
                return SiteDto.Response.builder()
                        .name(site.getName())
                        .build();
        }

        private static CertificateDto.Response getCertificateDto(Certificate certificate) {
                return CertificateDto.Response.builder()
                        .name(certificate.getName())
                        .build();
        }

        private static StackDto.Response getStackDto(Stack stack) {
                return StackDto.Response.builder()
                        .name(stack.getName())
                        .build();
        }

        private static MemberPoolDto.Response getMemberPoolResponseDto(Long memberId, Member findMember, List<TechFieldDto.Response> techFieldDtoList, List<StackDto.Response> stackDtoList, List<ProjectDto.Response> projectDtoList, List<CertificateDto.Response> certificateDtoList, List<SiteDto.Response> siteDtoList) {
                return MemberPoolDto.Response.builder()
                        .memberId(memberId)
                        .createTime(findMember.getCreateTime())
                        .nickName(findMember.getNickName())
                        .email(findMember.getEmail())
                        .imageUrl(findMember.getImageUrl())
                        .techField(techFieldDtoList)
                        .stack(stackDtoList)
                        .project(projectDtoList)
                        .certificate(certificateDtoList)
                        .site(siteDtoList)
                        .build();
        }

        private static ProjectDto.Response getProjectDto(Project project) {
                return ProjectDto.Response.builder()
                        .name(project.getName())
                        .startDate(project.getStartDate())
                        .endDate(project.getEndDate())
                        .url(project.getUrl())
                        .stack(
                                project.getStackList().stream()
                                        .map(
                                                stack -> getStackDto(stack)
                                        )
                                        .collect(Collectors.toList())
                        )
                        .build();
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
