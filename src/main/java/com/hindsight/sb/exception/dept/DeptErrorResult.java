package com.hindsight.sb.exception.dept;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DeptErrorResult {

    DUPLICATED_NAME(HttpStatus.BAD_REQUEST, "중복된 이름이 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
