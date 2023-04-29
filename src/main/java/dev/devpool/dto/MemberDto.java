package dev.devpool.dto;

import dev.devpool.domain.Member;
import dev.devpool.exception.member.read.MemberNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "member dto")
public class MemberDto {

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "member response dto")
    public static class Response {
        private String name;
        private String nickName;
        private String email;
        private String password;
        private String imageUrl;

    }
    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "member save request dto")
    public static class Save {

        public Save() {
        }

        private String name;
        private String nickName;
        private String email;
        private String password;
        private String imageUrl;


        public Member toEntity() {
            Member member = Member.builder()
                    .name(this.name)
                    .nickName(this.nickName)
                    .email(this.email)
                    .password(this.password)
                    .imageUrl(this.imageUrl)
                    .build();

            return member;
        }
    }
}
