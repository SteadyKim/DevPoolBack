package dev.devpool.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 나중에 쿼리로 insert만 해둘 예정
 */

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class PureStack {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PURESTACK_ID")
    private Long id;

    private String name;

}
