package dev.devpool.dto;

import dev.devpool.domain.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "memberPool dto")
public class MemberPoolDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "memberPool response dto")
    public static class Response {
        private Long memberId;

        private String nickName;

        private String email;

        private String imageUrl;

        private LocalDateTime createTime;

        private List<TechFieldDto.Response> techField;

        private List<StackDto.Response> stack;

        private List<ProjectDto.Response> project;

        private List<CertificateDto.Response> certificate;

        private List<SiteDto.Response> site;

    }
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "memberPool save request dto")
    public static class Save {


        private Long memberId;

        private List<TechFieldDto.Save> techField;

        private List<StackDto.Save> stack;

        private List<ProjectDto.Save> project;

        private List<CertificateDto.Save> certificate;

        private List<SiteDto.Save> site;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "memberPool update request dto")
    public static class Update {

        public Update() {
        }

        private List<TechFieldDto.Save> techField;

        private List<StackDto.Save> stack;

        private List<ProjectDto.Save> project;

        private List<CertificateDto.Save> certificate;

        private List<SiteDto.Save> site;

    }
}

