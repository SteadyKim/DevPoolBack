package dev.devpool.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID")
    private Long id;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<MemberTeam> memberTeams = new ArrayList<>();

    public List<MemberTeam> getMemberTeams() {
        return memberTeams;
    }


    private String title;

    private String body;

    private int totalNum;

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
