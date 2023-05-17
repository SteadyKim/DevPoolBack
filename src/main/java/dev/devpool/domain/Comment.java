package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "TEAM_ID")
    @ToString.Exclude
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    @ToString.Exclude
    private Comment parent;

    private String content;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    public void updateComment(Comment comment) {
        this.content = comment.getContent();
        this.createDate = LocalDateTime.now();
    }

    public void addTeam(Team team) {
        this.team = team;
    }

    public void addParent(Comment comment) {
        this.parent = comment;
    }
}
