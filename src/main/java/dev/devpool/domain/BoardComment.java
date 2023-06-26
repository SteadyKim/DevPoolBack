package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.devpool.dto.BoardCommentDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class BoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BoardComment_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    @JsonIgnore
    @ToString.Exclude
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    @ToString.Exclude
    private Member member;

    private String content;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    public void update(BoardCommentDto.Update boardCommentDto) {
        this.content = boardCommentDto.getContent();

    }
}
