package com.hindsight.sb.dto.course;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseRequest {
    @NotNull(message = "학생 ID를 입력하세요.")
    private Long studentId;
    @NotNull(message = "과목 ID를 입력하세요.")
    private Long subjectId;
}
