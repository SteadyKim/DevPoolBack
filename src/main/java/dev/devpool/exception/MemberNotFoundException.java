package dev.devpool.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(){
        super("멤버 조회 실패 - 멤버가 DB에 없습니다.");
    }
}
