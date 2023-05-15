package dev.devpool.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamDto {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "team response dto")
    public static class Response {
        private Long teamId;

        private String name;

        private String content;

        private int currentCount;

        private int recruitCount;

        private LocalDateTime createTime;

        private List<String> recruitTechField;

        private List<String> recruitStack;

        private String category;

        private MemberDto.Response hostMember;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "team save request dto")
    public static class Save {

        private Long hostMemberId;

        private String name;

        private String content;

        private int recruitCount;

        private CategoryDto.Save category;

        private List<TechFieldDto.Save> recruitTechField;

        private List<StackDto.Save> recruitStack;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(name = "team update request dto")
    public static class Update {

        public Update() {
        }

        private String name;

        private String content;

        private int recruitCount;

        private LocalDateTime createTime;

        private CategoryDto.Save category;

        private List<TechFieldDto.Save> recruitTechField;

        private List<StackDto.Save> recruitStack;


    }
}
