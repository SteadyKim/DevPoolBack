package dev.devpool.exception;

import dev.devpool.dto.common.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseDto<Object>> handleException(Exception e){
        log.info("[handleException] 모든 예외 처리");
        CommonResponseDto<Object> respDto= CommonResponseDto.builder()
                .message(e.getMessage() + e.getStackTrace())
                .build();
        return ResponseEntity.badRequest()
                .body(respDto);
    }


    @ExceptionHandler(CustomEntityNotFoundException.class)
    public ResponseEntity<Object> handleCustomEntityNotFoundException(CustomEntityNotFoundException e) {
        log.info("[handleCustomEntityNotFoundException] 에러 메세지: {}", e.getMessage());

        CommonResponseDto<Object> respDto = CommonResponseDto.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest()
                .body(respDto);
    }

    @ExceptionHandler(CustomDuplicateException.class)
    public ResponseEntity<Object> handleCustomDuplicateException(CustomDuplicateException e) {
        log.info("[handleCustomDuplicateException] 에러 메세지: {}", e.getMessage());

        CommonResponseDto<Object> respDto = CommonResponseDto.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.badRequest()
                .body(respDto);
    }



}
