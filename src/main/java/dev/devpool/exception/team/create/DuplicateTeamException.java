package dev.devpool.exception.team.create;

public class DuplicateTeamException extends RuntimeException{
    public DuplicateTeamException() {
        super("팀 저장 실패 - 중복된 팀이 있습니다.");
    }
}
