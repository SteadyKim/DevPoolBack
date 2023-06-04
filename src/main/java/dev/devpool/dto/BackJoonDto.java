package dev.devpool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Schema(description = "member BackJoon dto")
public class BackJoonDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "member BackJoon response dto")
    public static class Response {

        private Long memberId;

        private String BJId;

        private String nickName;

    }
}
