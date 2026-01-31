package com.labzin.document_service_itq_test_api.exception.handler;

import com.labzin.document_service_itq_test_api.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpStatus httpStatus) {

        if (httpStatus.value() >= 500) {
            log.error("response code: {} exception: {}, message: {}",
                    httpStatus.value(),
                    ex.getClass().getName(),
                    ex.getMessage()
            );
        } else {
            log.warn("response code: {} exception: {}, message: {}",
                    httpStatus.value(),
                    ex.getClass().getName(),
                    ex.getMessage()
            );
        }
        return ResponseEntity.status(httpStatus).body(new ErrorMessage(ex.getMessage()));
    }
}
