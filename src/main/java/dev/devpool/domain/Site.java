package dev.devpool.domain;

import dev.devpool.dto.SiteDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Site {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SITE_ID")
    private Long id;

    private String url;

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

    public SiteDto.Response toDto() {
        SiteDto.Response dto = SiteDto.Response.builder()
                .name(this.name)
                .build();

        return dto;
    }
}
