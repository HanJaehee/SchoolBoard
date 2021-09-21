package com.hindsight.sb.dto.course;

import com.hindsight.sb.dto.subject.SubjectResponse;
import com.hindsight.sb.entity.SubjectEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class CourseResponse {
    private List<SubjectResponse> subjectList;

    public CourseResponse toDto(List<SubjectEntity> subjectList) {
        return CourseResponse.builder()
                .subjectList(subjectList.stream().map(SubjectResponse::toDto).collect(Collectors.toList()))
                .build()
                ;
    }
}
