package dev.devpool.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Schema(description = "latter dto")
public class LatterDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "latter response dto")
    public static class Response {

        private Long latterId;

        private String senderNickName;

        private String receiverNickName;

        private String content;

        private LocalDateTime createTime;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "latter save request dto")
    public static class Save {

        private Long senderId;

        private Long receiverId;

        private String content;
    }
}
