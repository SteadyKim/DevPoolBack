package dev.devpool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Schema(name = "member save dto")
    public static class Save {

        private String name;

        private String nickName;

        private String email;

        private String password;

    }
}
