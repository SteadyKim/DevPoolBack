package dev.devpool.service;

import dev.devpool.domain.*;
import dev.devpool.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberPoolService {

        private final MemberService memberService;

        private final TechFieldService techFieldService;

        private final StackService stackService;

        private final ProjectService projectService;

        private final CertificateService certificateService;

        private final SiteService siteService;


        @Transactional
        public void join(MemberPoolDto.Save memberPoolDto) {
                Long memberId = memberPoolDto.getMemberId();
                Member findMember = memberService.findOneById(memberId);
                findMember.setMemberPoolCreateTime();

                List<TechFieldDto.Save> techFieldDtoList = memberPoolDto.getTechFieldDtoList();
                for (TechFieldDto.Save techFieldDto : techFieldDtoList) {
                        TechField techField = techFieldDto.toEntity(findMember);
                        techFieldService.join(techField);
                }

                List<StackDto.Save> stackDtoList = memberPoolDto.getStackDtoList();

                for (StackDto.Save stackDto : stackDtoList) {
                        Stack stack = stackDto.toEntity(findMember);
                        stackService.join(stack);
                }

                List<ProjectDto.Save> projectDtoList = memberPoolDto.getProjectDtoList();

                for (ProjectDto.Save projectDto : projectDtoList) {

                        Project project = projectDto.toEntity(findMember);
                        projectService.join(project);
                }


                List<CertificateDto.Save> certificateDtoList = memberPoolDto.getCertificateDtoList();
                for (CertificateDto.Save certificateDto : certificateDtoList) {
                        Certificate certificate = certificateDto.toEntity(findMember);
                        certificateService.join(certificate);
                }

                List<SiteDto.Save> siteDtoList = memberPoolDto.getSiteDtoList();
                for (SiteDto.Save siteDto : siteDtoList) {
                        Site site = siteDto.toEntity(findMember);
                        siteService.join(site);
                }

        }

        public MemberPoolDto.Response findOneById(Long memberId) {

                Member findMember = memberService.findOneById(memberId);

                List<TechFieldDto.Response> techFieldDtoList = techFieldService.findAllByMemberId(memberId)
                        .stream()
                        .map(TechField::toDto)
                        .collect(Collectors.toList());

                List<StackDto.Response> stackDtoList = stackService.findAllByMemberId(memberId)
                        .stream()
                        .map(Stack::toDto)
                        .collect(Collectors.toList());

                List<ProjectDto.Response> projectDtoList = projectService.findAllbyMemberId(memberId)
                        .stream()
                        .map(Project::toDto)
                        .collect(Collectors.toList());

                List<CertificateDto.Response> certificateDtoList = certificateService.findAllByMemberId(memberId)
                        .stream()
                        .map(Certificate::toDto)
                        .collect(Collectors.toList());

                List<SiteDto.Response> siteDtoList = siteService.findAllByMemberId(memberId)
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

}
