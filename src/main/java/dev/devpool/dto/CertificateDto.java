package dev.devpool.dto;

import dev.devpool.domain.Certificate;
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
@Schema(description = "certificate dto")
public class CertificateDto {

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "certificate response dto")
    public static class Response {

        private String name;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "certificate save request dto")
    public static class Save {

        public Save() {
        }

        private String name;


    }
}
