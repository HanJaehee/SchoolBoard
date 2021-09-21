package com.hindsight.sb.dto.subject;

import com.hindsight.sb.dto.user.UserResponse;
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
    private UserResponse supervisor;

    public static SubjectResponse toDto(SubjectEntity entity) {
        return SubjectResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .supervisor(UserResponse.toDto(entity.getSupervisor()))
                .build();
    }
}
