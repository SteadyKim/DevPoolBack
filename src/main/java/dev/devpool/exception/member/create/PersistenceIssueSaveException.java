package dev.devpool.exception.member.create;

import javax.persistence.PersistenceException;

public class PersistenceIssueSaveException extends RuntimeException {

    public PersistenceIssueSaveException() {
        super("멤버 저장 실패 - 인터넷 에러");
    }
}
