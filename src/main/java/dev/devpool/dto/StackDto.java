package dev.devpool.dto;

import dev.devpool.domain.Member;
import dev.devpool.domain.Stack;
import dev.devpool.domain.Team;
import dev.devpool.domain.TechField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
@Schema(description = "StackDto dto")
public class StackDto {

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "StackDto response dto")
    public static class Response {

        private String name;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "StackDto save request dto")
    public static class Save {

        public Save() {
        }

        private String name;


    }

}
