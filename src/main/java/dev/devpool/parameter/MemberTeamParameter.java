package dev.devpool.parameter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "memberTeam 저장 파라미터")
public class MemberTeamParameter {

    private Long memberId;

    private Long teamId;
}
