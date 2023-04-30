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

        private String title;

        private String body;

        private int totalNum;

        private LocalDateTime createTime;

        private List<String> techFieldNameList;

        private List<String> stackNameList;


    }

    @Data
    @Builder
    @AllArgsConstructor
    @Schema(description = "team save request dto")
    public static class Save {

        public Save() {
        }

        private Long memberId;
        private String title;

        private String body;

        private int totalNum;

        private LocalDateTime createTime;

        private List<String> techFieldNameList;

        private List<String> stackNameList;

        public Team toEntity() {

            Team team = Team.builder()
                    .title(this.title)
                    .body(this.body)
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

        private String title;

        private String body;

        private int totalNum;

        private LocalDateTime createTime;

        private List<String> techFieldNameList;

        private List<String> stackNameList;

        public Team toEntity() {

            Team team = Team.builder()
                    .title(this.title)
                    .body(this.body)
                    .build();

            return team;
        }
    }
}
