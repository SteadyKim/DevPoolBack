package dev.devpool.service;

import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import dev.devpool.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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

}
