package dev.devpool.dto;

import dev.devpool.domain.Certificate;
import dev.devpool.domain.Member;
import dev.devpool.domain.Site;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "site dto")
public class SiteDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "site response dto")
    public static class Response {

        private String name;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "site save request dto")
    public static class Save {

        private String name;

        private String url;


    }
}
