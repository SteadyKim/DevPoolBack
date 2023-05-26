package dev.devpool.exception;

public class CustomException extends RuntimeException {
    public CustomException(String errorName, String relatedClassName, String relatedMethod) {
        super("errorName: " + errorName + " relatedMethod: " + relatedClassName + " " + relatedMethod);
    }
}
