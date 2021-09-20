package com.hindsight.sb.exception.dept;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DeptException extends RuntimeException {
    private final DeptErrorResult errorResult;
}
