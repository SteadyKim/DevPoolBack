package dev.devpool.domain;

import dev.devpool.dto.ProjectDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROJECT_ID")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    Member member;

    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;

    String url;

    public ProjectDto.Response toDto() {

        ProjectDto.Response dto = ProjectDto.Response.builder()
                .name(this.name)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();

        return dto;
    }
}
