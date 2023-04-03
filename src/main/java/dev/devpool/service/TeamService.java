package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.MemberTeam;
import dev.devpool.domain.Team;
import dev.devpool.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Transactional
    public long join(Team team) {
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

    @Transactional
    public void deleteById(long teamId) {
        teamRepository.deleteById(teamId);
    }

    @Transactional
    public void delete(Team team) {
        teamRepository.delete(team);
    }

    @Transactional
    public void deleteAll() {
        teamRepository.deleteAll();
    }

    @Transactional
    public Team update(Long teamId, String name, String title, String body, int total_num) {
        Team findTeam = teamRepository.findOneById(teamId);
        // 변경감지를 활용해 Update 쿼리
        findTeam.setName(name);
        findTeam.setTitle(title);
        findTeam.setBody(body);
        findTeam.setTotal_num(total_num);

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
            memberTeam.setTeam(findTeam);
            memberTeam.setMember(member);

            findTeam.getMemberTeams().add(memberTeam);
        }

        return findTeam;
    }

}
