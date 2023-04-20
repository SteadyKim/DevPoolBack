package dev.devpool.exception;

import javax.persistence.PersistenceException;

public class PersistenceIssueSaveException extends RuntimeException {

    public PersistenceIssueSaveException() {
        super("멤버 저장 실패 - 인터넷 에러");
    }
}
