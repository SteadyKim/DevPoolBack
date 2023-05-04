package dev.devpool.exception.team.read;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(){
        super("팀 조회 실패 - 팀이 DB에 없습니다.");
    }
}
