package dev.devpool.dto;

import dev.devpool.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema(description = "member dto")
public class MemberDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "member response dto")
    public static class Response {

        private Long memberId;

        private String name;

        private String nickName;

        private String email;

        private String imageUrl;

    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Save {

        private String name;
        private String nickName;
        private String email;
        private String password;
        private String imageUrl;


    }
}
