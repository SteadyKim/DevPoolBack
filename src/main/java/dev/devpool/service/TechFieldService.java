package dev.devpool.service;


import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import dev.devpool.domain.TechField;
import dev.devpool.dto.TechFieldDto;
import dev.devpool.exception.CustomEntityNotFoundException;
import dev.devpool.repository.MemberRepository;
import dev.devpool.repository.TeamRepository;
import dev.devpool.repository.TechFieldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TechFieldService {

    private final TechFieldRepository techFieldRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    // 저장
    @Transactional
    public void join(TechField techField) {
        techFieldRepository.save(techField);
        return ;
    }

    // 조회
    public TechField findById(Long techFieldId) {
        TechField findTechField = techFieldRepository.findById(techFieldId)
                .orElse(null);
        return findTechField;
    }

    public List<TechField> findAllByMemberId(Long memberId) {
        List<TechField> findTechFields = techFieldRepository.findAllByMemberId(memberId);
        return findTechFields;
    }

    public List<TechField> findAllByTeamId(Long teamId) {
        List<TechField> findTechFields = techFieldRepository.findAllByTeamId(teamId);
        return findTechFields;
    }

    // 삭제
    @Transactional
    public void deleteById(Long techFieldId) {
        techFieldRepository.deleteById(techFieldId);
    }

    @Transactional
    public void deleteAllByMemberId(Long memberId) {
        techFieldRepository.deleteAllByMemberId(memberId);
    }

    @Transactional
    public void deleteAllByTeamId(Long teamId) {
        techFieldRepository.deleteAllByTeamId(teamId);
    }

    @Transactional
    public void deleteAll() {
        techFieldRepository.deleteAll();
    }

    // 수정
    @Transactional
    public void updateByMember(Long memberId, List<String> techFieldNameList) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomEntityNotFoundException(Member.class.getName(), memberId));
        // 지우기
        techFieldRepository.deleteAllByMemberId(findMember.getId());

        for (String techFieldName : techFieldNameList) {
            TechField techField = TechField.builder()
                    .member(findMember)
                    .name(techFieldName)
                    .build();
            techFieldRepository.save(techField);
        }
    }

    @Transactional
    public void updateByTeam(Long teamId, List<TechFieldDto.Save> techFieldDtoList) {
        Team findTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new CustomEntityNotFoundException(Team.class.getName(), teamId));
        // 지우기
        techFieldRepository.deleteAllByTeamId(findTeam.getId());

        for (TechFieldDto.Save techFieldDto : techFieldDtoList) {
            TechField techField = TechField.builder()
                    .team(findTeam)
                    .name(techFieldDto.getName())
                    .build();

            techFieldRepository.save(techField);
        }
    }
}
