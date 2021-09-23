package com.hindsight.sb.service;

import com.hindsight.sb.dto.dept.DeptRequest;
import com.hindsight.sb.dto.dept.DeptResponse;
import com.hindsight.sb.entity.DeptEntity;
import com.hindsight.sb.exception.dept.DeptErrorResult;
import com.hindsight.sb.exception.dept.DeptException;
import com.hindsight.sb.repository.DeptRepository;
import com.hindsight.sb.stub.DeptStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@MockitoSettings
public class DeptServiceTest {

    private final String name = "정보보호학과";
    @InjectMocks
    private DeptServiceImpl deptService;
    @Mock
    private DeptRepository deptRepository;

    @Test
    @DisplayName("전공 생성 실패 - 전공 이름 중복")
    void addDept_fail_duplicateName() {
        // given
        DeptRequest req = DeptStubs.generateRequest();
        doReturn(Optional.of(DeptEntity.builder().build())).when(deptRepository).findByName(name);

        // when
        final DeptException result = assertThrows(DeptException.class, () -> deptService.addDept(req));

        // then
        assertEquals(DeptErrorResult.DUPLICATED_NAME, result.getErrorResult());
    }

    @Test
    @DisplayName("전공 생성 및 조회 성공")
    void addDept_success() {
        // given
        DeptRequest req = DeptStubs.generateRequest();
        DeptEntity deptEntity = DeptStubs.generateStub();
        doReturn(Optional.empty()).when(deptRepository).findByName(name);
        doReturn(deptEntity).when(deptRepository).save(any(DeptEntity.class));

        // when
        DeptResponse deptResponse = deptService.addDept(req);

        // then
        assertNotNull(deptResponse.getId());
        assertEquals(name, deptResponse.getName());
    }
}
