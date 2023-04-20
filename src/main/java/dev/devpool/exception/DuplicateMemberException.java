package dev.devpool.exception;

public class DuplicateMemberException extends  RuntimeException {

    public DuplicateMemberException() {
        super("멤버 저장 실패 - 중복된 멤버가 있습니다.");
    }
}
