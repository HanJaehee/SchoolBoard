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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

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

    @Test
    @DisplayName("아이디로 전공 조회 실패 - Invalid deptId")
    void getDeptById_fail_InvalidDeptId() {
        // given
        doReturn(Optional.empty()).when(deptRepository).findById(any(Long.class));
        // when
        DeptException exception = assertThrows(DeptException.class, () -> deptService.getDeptById(1L));
        // then
        assertEquals(exception.getErrorResult(), DeptErrorResult.NO_SUCH_DEPT_ID);
    }

    @Test
    @DisplayName("아이디로 전공 조회 성공")
    void getDeptById() {
        // given
        DeptEntity dept = DeptStubs.generateStub();
        doReturn(Optional.of(dept)).when(deptRepository).findById(any(Long.class));
        // when
        DeptResponse deptById = deptService.getDeptById(dept.getId());
        // then
        assertEquals(deptById.getId(), dept.getId());
        assertEquals(deptById.getName(), dept.getName());
    }

    @Test
    @DisplayName("키워드 포함된 전공 조회 성공")
    void getAllDeptByName_success() {
        // given
        List<DeptEntity> deptEntityList = new ArrayList<>();
        IntStream.range(1, 10).forEach(x -> deptEntityList.add(DeptStubs.generateStub()));
        doReturn(deptEntityList).when(deptRepository).findAllByNameContains(any(String.class));
        // when
        List<DeptResponse> deptResponseList = deptService.getAllDeptByName("정보보호학과");
        // then
        assertEquals(deptResponseList.size(), deptEntityList.size());
    }
}
