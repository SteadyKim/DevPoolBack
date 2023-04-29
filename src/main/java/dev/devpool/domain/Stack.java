package dev.devpool.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stack {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stack_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM_ID")
    Team team;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PROJECT_ID")
    Project project;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
