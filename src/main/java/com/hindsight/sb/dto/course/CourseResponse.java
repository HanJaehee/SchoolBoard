package com.hindsight.sb.dto.course;

import com.hindsight.sb.dto.subject.SubjectDetailResponse;
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
    private List<SubjectDetailResponse> subjectList;

    public CourseResponse toDto(List<SubjectEntity> subjectList) {
        return CourseResponse.builder()
                .subjectList(subjectList.stream().map(SubjectDetailResponse::toDto).collect(Collectors.toList()))
                .build()
                ;
    }
}
