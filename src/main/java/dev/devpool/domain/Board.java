package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.devpool.dto.BoardDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    @JsonIgnore
    @ToString.Exclude
    private Member member;


    private String title;
    private String content;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    public void update(BoardDto.Update boardDto) {
        if (boardDto.getContent() != null) {
            this.content = boardDto.getContent();
        }

        if (boardDto.getTitle() != null) {
            this.title = boardDto.getTitle();
        }
    }
}
