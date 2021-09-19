package com.hindsight.sb.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorResult {

    DUPLICATED_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "이미 등록된 핸드폰 번호 입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
