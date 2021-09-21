package com.hindsight.sb.dto.subject;

import com.hindsight.sb.dto.user.UserBriefResponse;
import com.hindsight.sb.entity.SubjectEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SubjectResponse {
    private Long id;
    private String name;
    private UserBriefResponse prof;

    public static SubjectResponse toDto(SubjectEntity entity) {
        return SubjectResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .prof(UserBriefResponse.toDto(entity.getProf()))
                .build();
    }
}
