package com.hindsight.sb.stub;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.entity.DeptEntity;
import org.springframework.test.util.ReflectionTestUtils;

public class DeptStubs {

    private static final String deptName = "정보보호학과";
    private static long deptId = 1L;

    public static DeptEntity generateEntity() {
        DeptEntity deptEntity = DeptEntity.builder()
                .name(deptName)
                .build();
        ReflectionTestUtils.setField(deptEntity, "id", deptId++);
        return deptEntity;
    }

    public static DeptRequest generateRequest() {
        return DeptRequest.builder().name(deptName).build();
    }

}
