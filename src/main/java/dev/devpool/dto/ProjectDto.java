package dev.devpool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.devpool.domain.Member;
import dev.devpool.domain.Project;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.YearMonth;
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

        private List<StackDto.Response> stack;

        @DateTimeFormat(pattern = "yyyy-MM")
        private String startDate;

        @DateTimeFormat(pattern = "yyyy-MM")
        private String endDate;

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

        @DateTimeFormat(pattern = "yyyy-MM")
        private String startDate;

        @DateTimeFormat(pattern = "yyyy-MM")
        private String endDate;

        private List<StackDto.Save> stack;

        private String url;


    }
}
