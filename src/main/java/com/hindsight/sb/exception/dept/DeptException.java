package com.hindsight.sb.exception.dept;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeptException extends RuntimeException {
    private final DeptErrorResult errorResult;
}
