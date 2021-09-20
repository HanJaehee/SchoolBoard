package com.hindsight.sb.exception.subject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SubjectException extends RuntimeException {
    private final SubjectErrorResult errorResult;
}
