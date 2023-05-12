package dev.devpool.dto;

import dev.devpool.domain.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TeamDto {
    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "team response dto")
    public static class Response {
        private Long teamId;

        private String name;

        private String content;

        private int currentCount;

        private int recruitCount;

        private LocalDateTime createTime;

        private List<String> recruitTechFieldNameList;

        private List<String> recruitStackNameList;

        private String categoryName;

        private MemberDto.Response hostMember;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "team save request dto")
    public static class Save {

        public Save() {
        }

        private Long memberId;
        private String name;
        private String content;

        private int recruitNum;

        private CategoryDto.Save categoryName;

        private List<TechFieldDto.Save> recruitTechFieldNameList;

        private List<StackDto.Save> recruitStackNameList;

        public Team toEntity() {

            Team team = Team.builder()
                    .name(this.name)
                    .content(this.content)
                    .createTime(LocalDateTime.now())
                    .build();

            return team;
        }
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

        private CategoryDto.Save categoryName;

        private List<TechFieldDto.Save> recruitTechFieldNameList;

        private List<StackDto.Save> recruitStackNameList;

        public Team toEntity() {

            Team team = Team.builder()
                    .name(this.name)
                    .content(this.content)
                    .totalNum(this.recruitCount)
                    .createTime(this.getCreateTime())
                    .build();

            return team;
        }
    }
}
