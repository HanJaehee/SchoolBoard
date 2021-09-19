package com.hindsight.sb.exception;

import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.exception.error.UserErrorResult;
import com.hindsight.sb.exception.error.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(DeptException.class)
    public ResponseEntity<ErrorMsg> handleDeptException(DeptException e) {
        DeptErrorResult errorResult = e.getErrorResult();
        log.error(errorResult.getMessage());
        return ResponseEntity.status(errorResult.getHttpStatus()).body(ErrorMsg.builder().message(errorResult.getMessage()).build());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorMsg> handleUserException(UserException e) {
        UserErrorResult errorResult = e.getErrorResult();
        log.error(errorResult.getMessage());
        return ResponseEntity.status(errorResult.getHttpStatus()).body(ErrorMsg.builder().message(errorResult.getMessage()).build());
    }
}
