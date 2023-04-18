package dev.devpool.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(){
        super("Could not find member");
    }
}
