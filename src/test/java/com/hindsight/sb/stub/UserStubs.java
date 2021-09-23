package com.hindsight.sb.stub;

import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.dto.user.UserBriefResponse;
import com.hindsight.sb.dto.user.UserDetailResponse;
import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public class UserStubs {

    private static long userId = 1L;

    public static UserEntity generateEntity(UserRole type, String phoneNo, DeptEntity dept) {
        return UserEntity.builder()
                .name("한유저")
                .phoneNo(phoneNo)
                .userRole(type)
                .birth(LocalDate.of(1995, 11, 15))
                .address("경기도 의정부시")
                .deptEntity(dept)
                .build();
    }

    public static UserEntity generateStub(UserRole type, String phoneNo, DeptEntity dept) {
        UserEntity userEntity = generateEntity(type, phoneNo, dept);
        ReflectionTestUtils.setField(userEntity, "id", userId++);
        return userEntity;
    }


    public static UserRequest generateRequest(int userType, Long deptId, String phoneNo) {
        return UserRequest.builder()
                .name("한유저")
                .phoneNo(phoneNo)
                .birth("1995-11-15")
                .type(userType)
                .deptId(deptId)
                .address("경기도 의정부시")
                .build();
    }

    public static UserBriefResponse generateBriefResponse(String name, Long id) {
        return UserBriefResponse.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static UserDetailResponse generateDetailResponse(Long id, String phoneNo, UserRole userRole, DeptResponse dept) {
        return UserDetailResponse.builder()
                .id(id)
                .name("한유저")
                .phoneNo(phoneNo)
                .birth("1995-11-15")
                .userRole(userRole)
                .dept(dept)
                .address("경기도 의정부시")
                .build();
    }
}
