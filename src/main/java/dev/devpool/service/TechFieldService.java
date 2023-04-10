package dev.devpool.service;


import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import dev.devpool.domain.TechField;
import dev.devpool.repository.TechFieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TechFieldService {

    private final TechFieldRepository techFieldRepository;

    public TechFieldService(TechFieldRepository techFieldRepository) {
        this.techFieldRepository = techFieldRepository;
    }

    // 저장
    @Transactional
    public Long join(TechField techField) {
        Long techFieldId = techFieldRepository.save(techField);
        return techFieldId;
    }

    // 조회
    public TechField findById(Long techFieldId) {
        TechField findTechField = techFieldRepository.findOneById(techFieldId);
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
    public void updateWithMember(Member member, ArrayList<TechField> techFields) {
        // 지우기
        techFieldRepository.deleteAllByMemberId(member.getId());
        // 추가
        for (TechField techField : techFields) {
            techField.setMember(member);
            techFieldRepository.save(techField);
        }
    }

    @Transactional
    public void updateWithTeam(Team team, ArrayList<TechField> techFields) {
        // 지우기
        techFieldRepository.deleteAllByTeamId(team.getId());
        // 추가
        for (TechField techField : techFields) {
            techField.setTeam(team);
            techFieldRepository.save(techField);
        }
    }
}
