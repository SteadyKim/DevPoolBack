package dev.devpool.dto;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import dev.devpool.domain.Team;
import dev.devpool.domain.TechField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Schema(description = "techField dto")
public class TechFieldDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "techField response dto")
    public static class Response {

        private String name;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "techField save request dto")
    public static class Save {

        private String name;


    }
}
