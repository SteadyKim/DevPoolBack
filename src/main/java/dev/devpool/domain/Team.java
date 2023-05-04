package dev.devpool.domain;

import dev.devpool.dto.TeamDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<MemberTeam> memberTeams = new ArrayList<>();

    public List<MemberTeam> getMemberTeams() {
        return memberTeams;
    }

    @Column(name = "name")
    private String name;

    @Column(name = "body")
    private String body;

    @Column(name = "total_num")
    private int totalNum;

    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();
    /**
     * 비지니스 로직
     */
    public void update(Team team) {
        name = team.getName();
        body = team.getBody();
        totalNum = team.getTotalNum();
    }

    public TeamDto.Response toDto(List<Stack> stackList, List<TechField> techFieldList) {
        List<String> stackNameList = stackList.stream()
                .map(Stack::getName)
                .collect(Collectors.toList());

        List<String> techFieldNameList = techFieldList.stream()
                .map(TechField::getName)
                .collect(Collectors.toList());

        TeamDto.Response response = TeamDto.Response.builder()
                .name(this.name)
                .body(this.body)
                .totalNum(this.totalNum)
                .createTime(this.createTime)
                .stackNameList(stackNameList)
                .techFieldNameList(techFieldNameList)
                .build();

        return response;
    }

}
