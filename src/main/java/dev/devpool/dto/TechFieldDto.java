package dev.devpool.dto;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import dev.devpool.domain.Team;
import dev.devpool.domain.TechField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "techField dto")
public class TechFieldDto {
    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "techField response dto")
    public static class Response {

        private String name;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "techField save request dto")
    public static class Save {

        public Save() {
        }

        private String name;

        public TechField toEntity(Member member) {
            TechField techField = TechField.builder()
                    .member(member)
                    .name(this.name)
                    .build();

            return techField;
        }

        public TechField toEntity(Team team) {
            TechField techField = TechField.builder()
                    .team(team)
                    .name(this.name)
                    .build();

            return techField;
        }
    }
}
