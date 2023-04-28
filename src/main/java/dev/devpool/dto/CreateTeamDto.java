package dev.devpool.dto;

import dev.devpool.domain.Member;
import dev.devpool.domain.Stack;
import dev.devpool.domain.Team;
import dev.devpool.domain.TechField;
import lombok.Data;

import java.util.List;

@Data
public class CreateTeamDto {

    private Long memberId;
    private String title;

    private String body;

    private int totalNum;

    private List<String> techFieldNameList;

    private List<String> stackNameList;

    public Team toEntity(){
        Team team = new Team();

        team.setTitle(title);
        team.setBody(body);
        team.setTotalNum(totalNum);

        return team;
    }
}
