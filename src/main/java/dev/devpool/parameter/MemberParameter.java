package dev.devpool.parameter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@Schema(description = "member 저장 파라미터")
public class MemberParameter {

    private Long memberId;

    private String name;

    private String nickName;

    private String email;

    private String password;

    private String BJId;

    private MultipartFile image;
}
