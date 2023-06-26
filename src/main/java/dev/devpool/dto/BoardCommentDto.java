package dev.devpool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Schema(description = "BoardComment dto")
public class BoardCommentDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "BoardComment response dto")
    public static class Response {

        private Long boardCommentId;

        private Long boardId;

        private String memberNickName;

        private String content;

        private LocalDateTime createTime;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "BoardComment save dto")
    public static class Save {

        private Long memberId;

        private Long boardId;

        private String content;

    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "BoardComment update dto")
    public static class Update {

        private Long boardCommentId;

        private String content;

    }
}
