package com.hindsight.sb.exception.dept;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum DeptErrorResult {

    DUPLICATED_NAME(HttpStatus.BAD_REQUEST, "중복된 이름이 있습니다."),
    NO_SUCH_DEPT_ID(HttpStatus.BAD_REQUEST, "요청 하신 ID와 일치하는 과목이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
