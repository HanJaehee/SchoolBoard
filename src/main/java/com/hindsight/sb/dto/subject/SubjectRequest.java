package com.hindsight.sb.dto.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SubjectRequest {
    @NotNull(message = "이름을 입력해주세요.")
    private String name;
}
