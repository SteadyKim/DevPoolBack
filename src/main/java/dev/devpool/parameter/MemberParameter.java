package dev.devpool.parameter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "member 파라미터")
public class MemberParameter {
    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "memberParameter Save dto")
    public static class Save {

        private String name;

        private String nickName;

        private String email;

        private String password;

        private String BJId;

        private MultipartFile image;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "memberParameter Save dto")
    public static class Update {

        private Long memberId;

        private String name;

        private String nickName;

        private String email;

        private String password;

        private String BJId;

        private MultipartFile image;

    }
}
