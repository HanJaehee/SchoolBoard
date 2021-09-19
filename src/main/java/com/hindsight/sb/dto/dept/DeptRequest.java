package com.hindsight.sb.dto.dept;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DeptRequest {
    @NotNull(message = "전공 이름을 입력하세요.")
    private String name;
}
