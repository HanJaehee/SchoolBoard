package com.hindsight.sb.dto.user;

import com.hindsight.sb.entity.UserEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserBriefResponse {
    private Long id;
    private String name;

    public static UserBriefResponse toDto(UserEntity entity) {
        return UserBriefResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
