package dev.devpool.dto;

import dev.devpool.domain.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "memberPool dto")
public class MemberPoolDto {
    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "memberPool response dto")
    public static class Response {
        private Long memberId;

        private String nickName;

        private String email;

        private String imageUrl;

        private LocalDateTime createTime;

        private List<TechFieldDto.Response> techFieldDtoList;

        private List<StackDto.Response> stackDtoList;

        private List<ProjectDto.Response> projectDtoList;

        private List<CertificateDto.Response> certificateDtoList;

        private List<SiteDto.Response> siteDtoList;

    }
    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "memberPool save request dto")
    public static class Save {

        public Save() {
        }

        private Long memberId;

        private List<TechFieldDto.Save> techFieldDtoList;

        private List<StackDto.Save> stackDtoList;

        private List<ProjectDto.Save> projectDtoList;

        private List<CertificateDto.Save> certificateDtoList;

        private List<SiteDto.Save> siteDtoList;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "memberPool update request dto")
    public static class Update {

        public Update() {
        }

        private List<TechFieldDto.Save> techFieldDtoList;

        private List<StackDto.Save> stackDtoList;

        private List<ProjectDto.Save> projectDtoList;

        private List<CertificateDto.Save> certificateDtoList;

        private List<SiteDto.Save> siteDtoList;

    }
}

