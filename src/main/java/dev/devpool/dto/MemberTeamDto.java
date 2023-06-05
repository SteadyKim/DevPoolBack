package dev.devpool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Schema(description = "memberTeam dto (팀원)")
public class MemberTeamDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "memberTeam response dto (팀원)")
    public static class Response {
        private Long memberId;

        private String nickName;

    }
}
