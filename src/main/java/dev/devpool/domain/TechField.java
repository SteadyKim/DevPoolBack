package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.devpool.dto.TechFieldDto;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class TechField {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TECHFIELD_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    private String name;


}
