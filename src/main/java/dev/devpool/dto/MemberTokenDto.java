package dev.devpool.dto;

import dev.devpool.jwt.TokenInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "memberToken dto")
public class MemberTokenDto {
    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "memberToken response dto")
    public static class Response {

        private MemberDto.Response memberDto;

        private TokenInfo tokenInfo;

    }
}
