package com.hindsight.sb.exception.dept;

import com.hindsight.sb.controller.DeptController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = DeptController.class)
@Slf4j
public class DeptExceptionHandler {

    @ExceptionHandler(DeptException.class)
    public ResponseEntity<String> handleDeptException(DeptException e) {
        DeptErrorResult errorResult = e.getErrorResult();
        log.error(errorResult.getMessage());
        return ResponseEntity.status(errorResult.getHttpStatus()).body(errorResult.getMessage());
    }

}
