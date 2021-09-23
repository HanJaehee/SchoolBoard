package com.hindsight.sb.stub;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.entity.DeptEntity;
import org.springframework.test.util.ReflectionTestUtils;

public class DeptStubs {

    private static final String deptName = "정보보호학과";
    private static long deptId = 1L;

    public static DeptEntity generateEntity() {
        return DeptEntity.builder()
                .name(deptName)
                .build();
    }

    public static DeptEntity generateStub() {
        DeptEntity deptEntity = generateEntity();
        ReflectionTestUtils.setField(deptEntity, "id", deptId++);
        return deptEntity;
    }


    public static DeptRequest generateRequest() {
        return DeptRequest.builder().name(deptName).build();
    }

    public static DeptResponse generateResponse(Long id) {
        return DeptResponse.builder()
                .id(id)
                .name(deptName)
                .build();
    }
}
