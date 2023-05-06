package dev.devpool.exception.member.read;

public class MemberPoolNotFoundException extends RuntimeException {
    public MemberPoolNotFoundException(){
        super("멤버 풀 조회 실패 - 멤버 풀이 DB에 없습니다.");
    }
}
