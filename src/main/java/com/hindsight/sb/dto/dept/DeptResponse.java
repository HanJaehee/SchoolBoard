package com.hindsight.sb.dto.dept;

import com.hindsight.sb.entity.DeptEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DeptResponse {

    private Long id;
    private String name;

    public static DeptResponse toDto(DeptEntity dept) {
        return DeptResponse.builder()
                .id(dept.getId())
                .name(dept.getName())
                .build();
    }
}
