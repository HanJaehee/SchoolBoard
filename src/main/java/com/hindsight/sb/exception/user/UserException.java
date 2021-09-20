package com.hindsight.sb.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserException extends RuntimeException {
    private final UserErrorResult errorResult;
}
