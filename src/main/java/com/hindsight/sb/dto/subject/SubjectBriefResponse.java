package com.hindsight.sb.dto.subject;

import com.hindsight.sb.entity.SubjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SubjectBriefResponse {
    private Long id;
    private String name;

    public static SubjectBriefResponse toDto(SubjectEntity entity) {
        return SubjectBriefResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
