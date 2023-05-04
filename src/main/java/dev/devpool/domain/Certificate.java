package dev.devpool.domain;

import dev.devpool.dto.CertificateDto;
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

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    Member member;


    /**
     * Set 메서드
     */
    public void addMember(Member member) {
        this.member = member;
    }

    public CertificateDto.Response toDto() {
        CertificateDto.Response dto = CertificateDto.Response.builder()
                .name(this.name)
                .build();

        return dto;
    }

}
