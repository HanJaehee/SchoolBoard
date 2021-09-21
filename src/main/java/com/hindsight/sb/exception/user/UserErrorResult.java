package com.hindsight.sb.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserErrorResult {

    DUPLICATED_PHONE_NUMBER(HttpStatus.BAD_REQUEST, "이미 등록된 핸드폰 번호 입니다."),
    NOT_EXISTS_USER(HttpStatus.BAD_REQUEST, "등록된 유저가 없습니다."),
    IS_NOT_STUDENT(HttpStatus.BAD_REQUEST, "해당 유저는 학생이 아닙니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
