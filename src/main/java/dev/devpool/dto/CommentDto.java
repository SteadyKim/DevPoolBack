package dev.devpool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "comment dto")
public class CommentDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "comment response dto")
    public static class Response {

        private Long teamId;

        private Long commentId;

        private String content;

        private String nickName;

        private LocalDateTime createTime;

        private List<CommentDto.Response> replies;

    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "comment save request dto")
    public static class Save {

        private Long memberId;

        private String content;

    }

}
