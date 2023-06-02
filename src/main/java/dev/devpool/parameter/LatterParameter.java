package dev.devpool.parameter;

import dev.devpool.dto.CommentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "latter 파라미터")
public class LatterParameter {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "comment response dto")
    public static class Delete {

        private Long senderId;

        private Long receiverId;

    }
}
