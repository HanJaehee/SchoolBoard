package com.hindsight.sb.stub;

import com.hindsight.sb.dto.user.UserRequest;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.entity.UserEntity;
import com.hindsight.sb.entity.UserRole;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

public class UserStubs {

    private static long userId = 1L;

    public static UserEntity generateEntity(UserRole type, String phoneNo, DeptEntity dept) {
        UserEntity userEntity = UserEntity.builder()
                .name("한학생")
                .phoneNo(phoneNo)
                .userRole(type)
                .birth(LocalDate.now())
                .address("경기도 의정부시")
                .deptEntity(dept)
                .build();
        ReflectionTestUtils.setField(userEntity, "id", userId++);
        return userEntity;
    }

    public static UserRequest generateRequest(int userType, Long deptId, String phoneNo) {
        return UserRequest.builder()
                .name("한재희")
                .phoneNo(phoneNo)
                .birth("1995-00-00")
                .type(userType)
                .deptId(deptId)
                .address("경기도 의정부시")
                .build();
    }
}
