package dev.devpool.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CERTIFICATE_ID")
    private Long id;

    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    Member member;


    /**
     * Set 메서드
     */
    public void addMember(Member member) {
        this.member = member;
    }

}
