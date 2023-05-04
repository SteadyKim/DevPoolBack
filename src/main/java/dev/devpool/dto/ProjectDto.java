package dev.devpool.dto;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "project dto")
public class ProjectDto {

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "project response dto")
    public static class Response {

        private String name;

        private LocalDate startDate;

        private LocalDate endDate;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "project save request dto")
    public static class Save {

        public Save() {
        }

        private String name;

        private LocalDate startDate;

        private LocalDate endDate;


        public Project toEntity(Member member) {
            Project project = Project.builder()
                    .member(member)
                    .name(this.name)
                    .startDate(this.startDate)
                    .endDate(this.endDate)
                    .build();

            return project;
        }
    }
}
