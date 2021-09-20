package com.hindsight.sb.exception.subject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SubjectErrorResult {

    DUPLICATE_NAME(HttpStatus.BAD_REQUEST, "과목 이름이 중복되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
