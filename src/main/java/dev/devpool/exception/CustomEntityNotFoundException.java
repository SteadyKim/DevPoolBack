package dev.devpool.exception;

public class CustomEntityNotFoundException extends RuntimeException{
    public CustomEntityNotFoundException(String className, Long id) {
        super("Entity name: ".concat(className)
                .concat(" / ")
                .concat(" id: ".concat(id.toString())
                        .concat(" -> db에 존재하지 않습니다.")));
    }


}
