package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.devpool.domain.enums.IsCheck;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "SENDER_ID")
    @JsonIgnore
    @ToString.Exclude
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID")
    @JsonIgnore
    @ToString.Exclude
    private Member receiver;

    private String content;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();
//
//    @Enumerated(STRING)
//    private IsCheck isCheck;


}
