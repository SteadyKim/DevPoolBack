package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import dev.devpool.exception.member.create.DuplicateMemberException;
import dev.devpool.exception.team.create.DuplicateTeamException;
import dev.devpool.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public long join(Team team) {
        validateTeam(team);

        teamRepository.save(team);
        return team.getId();
    }

    public Team findOneById(long teamId) {
        Team findTeam = teamRepository.findOneById(teamId);

        return findTeam;
    }

    public List<Team> findAll() {
        List<Team> teamList = teamRepository.findAll();
        return teamList;
    }

    public void validateTeam(Team team) {
        Optional<Team> findTeam = teamRepository.findOneByTitle(team.getTitle());

        if (findTeam.isPresent()) {
            throw new DuplicateTeamException();
        }
    }
    @Transactional
    public void deleteById(long teamId) {
        teamRepository.deleteById(teamId);
    }

    @Transactional
    public void deleteAll() {
        teamRepository.deleteAll();
    }

    @Transactional
    public Team update(Long teamId, Team newTeam) {
        Team findTeam = teamRepository.findOneById(teamId);
        // 변경감지를 활용해 Update 쿼리
        findTeam.update(newTeam);


        return findTeam;
    }

    /**
     * MemberTeam
     */
    @Transactional
    public Team updateMemberTeam(Long teamId, Member... members) {
        Team findTeam = teamRepository.findOneById(teamId);
        // 지우고 추가하기...
        teamRepository.deleteAllMemberTeam(findTeam.getId());

        for (Member member : members) {
            MemberTeam memberTeam = new MemberTeam();

            memberTeam.addMemberTeam(member, findTeam);
        }

        return findTeam;
    }

}
