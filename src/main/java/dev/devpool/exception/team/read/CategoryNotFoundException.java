package dev.devpool.exception.team.read;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException() {
        super("카테고리 조회 실패 - 카테고리가 DB에 없습니다.");
    }
}
