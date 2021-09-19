package com.hindsight.sb.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private UserErrorResult errorResult;
}
