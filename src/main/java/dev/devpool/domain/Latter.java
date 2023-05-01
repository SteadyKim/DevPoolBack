package dev.devpool.domain;

import dev.devpool.domain.enums.IsCheck;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Latter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LATTER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String body;

    private LocalDate createDate;

    @Enumerated(STRING)
    private IsCheck isCheck;

    /**
     * Set 메서드
     */
    public void addMember(Member member) {
        this.member = member;
    }
}
