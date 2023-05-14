package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID")
    Member member;

}
