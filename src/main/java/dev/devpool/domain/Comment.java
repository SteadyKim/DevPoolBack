package dev.devpool.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


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
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    private String content;

    private LocalDate createDate;

    public void updateComment(Comment comment) {
        this.content = comment.getContent();
        this.createDate = LocalDate.now();
    }

    public void addTeam(Team team) {
        this.team = team;
    }

    public void addParent(Comment comment) {
        this.parent = comment;
    }
}
