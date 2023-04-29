package dev.devpool.domain;

import dev.devpool.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "total_num")
    private int totalNum;

    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();


    public TeamDto.Response toDto(List<Stack> stackList, List<TechField> techFieldList) {
        List<String> stackNameList = stackList.stream()
                .map(Stack::getName)
                .collect(Collectors.toList());

        List<String> techFieldNameList = techFieldList.stream()
                .map(TechField::getName)
                .collect(Collectors.toList());

        TeamDto.Response response = TeamDto.Response.builder()
                .title(this.title)
                .body(this.body)
                .totalNum(this.totalNum)
                .createTime(this.createTime)
                .stackNameList(stackNameList)
                .techFieldNameList(techFieldNameList)
                .build();

        return response;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int total_num) {
        this.totalNum = total_num;
    }
}
