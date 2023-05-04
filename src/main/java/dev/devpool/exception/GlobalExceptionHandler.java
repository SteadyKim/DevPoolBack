package dev.devpool.exception;

import dev.devpool.dto.CommonResponseDto;
import dev.devpool.exception.member.create.DuplicateMemberException;
import dev.devpool.exception.member.delete.DeleteMemberNotFound;
import dev.devpool.exception.member.read.MemberNotFoundException;
import dev.devpool.exception.member.create.PersistenceIssueSaveException;
import dev.devpool.exception.team.create.DuplicateTeamException;
import dev.devpool.exception.team.delete.DeleteTeamNotFoundException;
import dev.devpool.exception.team.read.TeamNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CommonResponseDto<Object>> handleException(Exception e){
//        log.info("[handleException] 모든 예외 처리");
//        CommonResponseDto<Object> respDto= CommonResponseDto.builder()
//                .message(e.getMessage())
//                .build();
//        return ResponseEntity.badRequest()
//                .body(respDto);
//    }

    /**
     * 멤버
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateMemberException(DuplicateMemberException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PersistenceIssueSaveException.class)
    public ResponseEntity<ErrorResponse> handlePersistenceIssueSaveException(PersistenceIssueSaveException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(DeleteMemberNotFound.class)
    public ResponseEntity<ErrorResponse> handleDeleteMemberNotFoundException(DeleteMemberNotFound ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * 팀
     */
    @ExceptionHandler(DuplicateTeamException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTeamException(DuplicateTeamException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DeleteTeamNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDeleteTeamNotFoundException(DeleteTeamNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTeamNotFoundException(TeamNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}
