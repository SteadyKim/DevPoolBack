package dev.devpool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.devpool.dto.ProjectDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    @JoinColumn(name = "MEMBER_ID")
    Member member;

    @OneToMany(mappedBy = "project", fetch = LAZY)
    @JsonIgnore
    List<Stack> stackList = new ArrayList<>();

    String name;

    @DateTimeFormat(pattern = "yyyy-MM")
    YearMonth startDate;


    @DateTimeFormat(pattern = "yyyy-MM")
    YearMonth endDate;

    String url;

}
