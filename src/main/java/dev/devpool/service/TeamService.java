package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import dev.devpool.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public long join(Team team) {
        teamRepository.save(team);
        return team.getId();
    }

    public Team findOne(long teamId) {
        Team findTeam = teamRepository.findOne(teamId);

        return findTeam;
    }

    public List<Team> findAll() {
        List<Team> teamList = teamRepository.findAll();

        return teamList;
    }

}
