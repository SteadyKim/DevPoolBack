package dev.devpool.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.devpool.dto.ProjectDto;
import dev.devpool.dto.StackDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Stack {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "stack_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID")
    Member member;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "TEAM_ID")
    Team team;

    @ManyToOne(fetch = LAZY)
    @JsonIgnore
    @JoinColumn(name = "PROJECT_ID")
    Project project;

    private String name;

}
