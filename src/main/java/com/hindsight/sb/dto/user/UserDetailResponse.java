package com.hindsight.sb.dto.user;

import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.dto.subject.SubjectBriefResponse;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDetailResponse {

    private Long id;
    private String name;
    private String address;
    private String phoneNo;
    private String birth;
    private UserRole userRole;
    private DeptResponse dept;
    private List<SubjectBriefResponse> subjects;

    public static UserDetailResponse toDto(UserEntity entity, List<SubjectBriefResponse> subjects) {
        return UserDetailResponse.builder()
                .id(entity.getId())
                .address(entity.getAddress())
                .name(entity.getName())
                .birth(entity.getBirth().toString())
                .phoneNo(entity.getPhoneNo())
                .userRole(entity.getUserRole())
                .dept(DeptResponse.toDto(entity.getDeptEntity()))
                .subjects(subjects)
                .build();
    }
}
