package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.devpool.dto.TeamDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MemberTeam> memberTeams = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_MEMBER_ID")
    private Member hostMember;

    public List<MemberTeam> getMemberTeams() {
        return memberTeams;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "total_num")
    private int recruitNum;

    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * 비지니스 로직
     */
    public void update(TeamDto.Update teamDto) {
        name = teamDto.getName();
        content = teamDto.getContent();
        recruitNum = teamDto.getRecruitCount();
    }


}
