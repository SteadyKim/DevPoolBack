package dev.devpool.exception.member.delete;

public class DeleteMemberNotFound extends RuntimeException {
    public DeleteMemberNotFound() {
        super("멤버 삭제 실패 - 멤버가 DB에 없습니다.");
    }
}
