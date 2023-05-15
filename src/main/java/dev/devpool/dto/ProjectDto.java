package dev.devpool.dto;

import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "project dto")
public class ProjectDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "project response dto")
    public static class Response {

        private String name;

        private List<StackDto.Response> stackDtoList;

        private LocalDate startDate;

        private LocalDate endDate;

        private String url;

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

        private List<StackDto.Save> stack;

        private LocalDate endDate;

        private String url;


    }
}
