package dev.devpool.exception.team.delete;

public class DeleteTeamNotFoundException extends RuntimeException {
    public DeleteTeamNotFoundException() {
        super("팀 삭제 실패 - 팀이 DB에 없습니다.");
    }
}
