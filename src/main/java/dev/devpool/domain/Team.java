package dev.devpool.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private long id;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<MemberTeam> memberTeams = new ArrayList<>();

    public List<MemberTeam> getMemberTeams() {
        return memberTeams;
    }

    public void setMemberTeams(List<MemberTeam> memberTeams) {
        this.memberTeams = memberTeams;
    }

    private String name;

    private String title;

    private String body;

    private int recruited_num;

    private int total_num;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRecruited_num() {
        return recruited_num;
    }

    public void setRecruited_num(int recruited_num) {
        this.recruited_num = recruited_num;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }
}
