package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberTeam {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public void addMemberTeam(Member member, Team team) {
        team.getMemberTeams().add(this);
        addTeam(team);
        addMember(member);
    }

    public void addTeam(Team team) {
        this.team = team;
    }

    public void addMember(Member member) {
        this.member = member;
    }

}
