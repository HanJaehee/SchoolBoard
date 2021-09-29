package com.hindsight.sb.dto.subject;

import com.hindsight.sb.dto.user.UserBriefResponse;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubjectAndStudentListResponse {
    SubjectDetailResponse subjectResponse;
    List<UserBriefResponse> studentList;

    private static SubjectAndStudentListResponse toDto(SubjectDetailResponse subjectResponse, List<UserBriefResponse> studentList) {
        return SubjectAndStudentListResponse.builder()
                .subjectResponse(subjectResponse)
                .studentList(studentList)
                .build();
    }
}
