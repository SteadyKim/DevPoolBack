package dev.devpool.dto;

import dev.devpool.domain.Category;
import dev.devpool.domain.Certificate;
import dev.devpool.domain.Member;
import dev.devpool.domain.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "category dto")
public class CategoryDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "category save request dto")
    public static class Save {


        private String name;


    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "category response dto")
    public static class Response {

        private String name;

    }
}
