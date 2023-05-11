package dev.devpool.exception;

public class CustomDuplicateException extends RuntimeException{
    public CustomDuplicateException(String className, Long id) {
        super("Entity name: ".concat(className)
                .concat(" / ")
                .concat(" id: ".concat(id.toString())
                        .concat(" -> 데이터가 중복입니다.")));
    }
}
