package dev.devpool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Board dto")
public class BoardDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "board response dto")
    public static class Response {

        private Long boardId;

        private String title;

        private String content;

        private String memberNickName;

        private List<BoardCommentDto.Response> commentList;

        private LocalDateTime createTime;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "board save dto")
    public static class Save {

        private Long memberId;

        private String title;

        private String content;

    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "board update dto")
    public static class Update {

        private Long boardId;

        private String title;

        private String content;


    }
}